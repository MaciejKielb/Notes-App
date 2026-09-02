package com.notesapp.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.UserPreferences
import com.notesapp.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferences: UserPreferences,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    private val _isFirstTime = MutableStateFlow<Boolean?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading

    private val isFirstTime: StateFlow<Boolean?> = _isFirstTime

    init {
        viewModelScope.launch {
            userPreferences.isFirstTime().collect { firstRun ->
                _isFirstTime.value = firstRun
                _isLoading.value = false
            }
        }
    }

    fun startDestination(): String {
        Log.e("SplashViewModel", "isLoading: ${isLoading.value}")
        Log.e("SplashViewModel", "isFirstTime: ${isFirstTime.value}")
        return if (isFirstTime.value == true) {
            Screens.GettingStartedScreen.route
        } else {
            Screens.MainScreen.route
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            userPreferences.setOnboardingCompleted()
        }
    }
}
