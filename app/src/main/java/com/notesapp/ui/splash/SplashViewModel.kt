package com.notesapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferences: UserPreferences,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    private val _isFirstTime = MutableStateFlow<Boolean?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading

    val isFirstTime: StateFlow<Boolean?> = _isFirstTime

    init {
        viewModelScope.launch {
            userPreferences.isFirstTime().collect { firstRun ->
                _isFirstTime.value = firstRun
                _isLoading.value = false
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            userPreferences.setOnboardingCompleted()
        }
    }
}
