package com.example.githubtest.ui.repositories

import com.example.githubtest.domain.RepositoriesUseCase
import com.example.githubtest.domain.model.Repository
import com.example.githubtest.ui.common.BaseViewModel
import com.example.githubtest.ui.common.recycler.ItemModel
import com.example.githubtest.ui.mapping.toItemModel
import com.example.githubtest.ui.navigation.NavigationCommand
import com.example.githubtest.ui.navigation.Router
import com.example.githubtest.ui.navigation.Screen
import com.example.githubtest.ui.repositories.adapter.ProgressItemModel
import com.example.githubtest.ui.repositories.adapter.RepositoryItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repositoriesUseCase: RepositoriesUseCase,
    private val router: Router
) : BaseViewModel() {
    private val _stateFlow = MutableStateFlow<State>(State())
    val stateFlow: Flow<State> = _stateFlow.asStateFlow()

    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    private var searchJob: Job? = null
    private var loadNextPageJob: Job? = null
    private var scrollToStart: Boolean = false
    private var repositoriesList: List<Repository> = emptyList()

    init {
        launch {
            repositoriesUseCase.repositoriesFlow.collect { repositories ->
                _stateFlow.update { state ->
                    repositoriesList = repositories
                    val newList = if (state.isLoadingNextPage) {
                        repositories.map { it.toItemModel() } + ProgressItemModel()
                    } else {
                        repositories.map { it.toItemModel() }
                    }
                    state.copy(data = newList, scrollToStart = scrollToStart)
                }
            }
        }
    }

    fun onTextChanged(query: String) {
        if (query == _stateFlow.value.query) return
        searchJob?.cancel()
        if (loadNextPageJob?.isActive == true) {
            loadNextPageJob?.cancel()
            _stateFlow.update { state ->
                state.copy(
                    data = repositoriesList.map { it.toItemModel() },
                    isLoadingNextPage = false
                )
            }
        }
        if (query.isEmpty()) {
            launch {
                repositoriesUseCase.clearSearchResults()
            }
            return
        }

        searchJob = launch(onFinally = {
            _stateFlow.update { it.copy(isLoading = false) }
        }) {
            delay(350)
            scrollToStart = true
            _stateFlow.update {
                it.copy(
                    query = query,
                    isLoading = true,
                    scrollToStart = false
                )
            }
            repositoriesUseCase.searchRepositories(query)
        }
    }

    fun loadNextPage() {
        if (loadNextPageJob?.isActive == true || searchJob?.isActive == true) return
        loadNextPageJob = launch(onFinally = {
            _stateFlow.update { state ->
                state.copy(
                    data = repositoriesList.map { it.toItemModel() },
                    isLoadingNextPage = false
                )
            }
        }) {
            _stateFlow.update { state ->
                state.copy(
                    data = repositoriesList.map { it.toItemModel() } + ProgressItemModel(),
                    isLoadingNextPage = true,
                    scrollToStart = false
                )
            }
            scrollToStart = false
            repositoriesUseCase.loadNextPage()
        }
    }

    fun onRepoClicked(item: RepositoryItemModel) {
        launch {
            repositoriesList.firstOrNull { it.id == item.id }?.also { item ->
                item.url?.also { url ->
                    repositoriesUseCase.repositoryOpened(item)
                    _actionFlow.emit(Action.OpenRepository(url))
                }
            }
        }
    }

    fun onHistoryClicked() {
        launch {
            router.navigate(NavigationCommand.Navigate(Screen.History))
        }
    }

    data class State(
        val data: List<ItemModel> = emptyList(),
        val query: String = "",
        val scrollToStart: Boolean = false,
        val isLoading: Boolean = false,
        val isLoadingNextPage: Boolean = false
    )

    sealed class Action {
        class OpenRepository(val url: String) : Action()
    }
}