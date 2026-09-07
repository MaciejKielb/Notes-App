package com.notesapp.ui.splash

import androidx.lifecycle.ViewModel
import com.notesapp.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class SplashViewModel(
    private val userPreferences: UserPreferences,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun awaitIsFirstLaunch(): Boolean = userPreferences.isFirstTime().first()

    fun finishSplash() {
        _isLoading.value = false
    }
}
