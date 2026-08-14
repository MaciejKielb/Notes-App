package com.notesapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferences(
    private val context: Context,
) {
    private val isFirstTimeKey = booleanPreferencesKey("isFirstTime")

    fun isFirstTime() = context.dataStore.data.map { preferences -> preferences[isFirstTimeKey] ?: true }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit {
            preferences -> preferences[isFirstTimeKey] = false
        }
    }
}

