package com.example.githubtest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface AuthDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): Flow<String?>
}

class AuthDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthDataSource {

    override suspend fun saveToken(token: String) {
        save {
            this[KEY_TOKEN] = token
        }
    }

    override suspend fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }
    }

    private suspend fun save(transform: MutablePreferences.() -> Unit) {
        dataStore.edit { preferences ->
            preferences.transform()
        }
    }

    private companion object {
        val KEY_TOKEN = stringPreferencesKey("key_token")
    }
}