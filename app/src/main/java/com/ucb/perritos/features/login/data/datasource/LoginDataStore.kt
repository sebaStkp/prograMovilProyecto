package com.ucb.perritos.features.login.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "user_preferences")
class LoginDataStore (
    private val context: Context
) {
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val TOKEN = stringPreferencesKey("token")
    }


    suspend fun saveUserName(userName: String) {
        context.dataStore.edit {
                preferences -> preferences[USER_NAME] = userName
        }
    }


    suspend fun getUserName(): Result<String> {
        val preferences = context.dataStore.data.first()
        return if (preferences[USER_NAME] != null) {
            Result.success(preferences[USER_NAME]!!)
        } else {
            Result.failure(Exception("User name not found"))
        }
    }
    suspend fun saveToken(token: String) {
        context.dataStore.edit {
                preferences -> preferences[TOKEN] = token
        }
    }


    suspend fun getToken(): String? {
        val preferences = context.dataStore.data.first()
        return if (preferences[TOKEN] != null) {
            preferences[TOKEN]!!
        } else {
            null
        }
    }
}
