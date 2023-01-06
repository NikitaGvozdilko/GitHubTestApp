package com.example.githubtest.ui.history

import com.example.githubtest.domain.HistoryUseCase
import com.example.githubtest.ui.common.BaseViewModel
import com.example.githubtest.ui.mapping.toItemModel
import com.example.githubtest.ui.repositories.adapter.RepositoryItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyUseCase: HistoryUseCase
) : BaseViewModel() {

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow: Flow<State> = _stateFlow.asStateFlow()

    init {
        launch {
            historyUseCase.getHistoryRepositories().collect { repositories ->
                _stateFlow.update { state ->
                    state.copy(list = repositories.map { it.toItemModel() })
                }
            }
        }
    }


    data class State(val list: List<RepositoryItemModel> = emptyList())
}