package com.notesapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.UserPreferences
import com.notesapp.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferences: UserPreferences,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    private var isFirstTime: Boolean = true
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            isFirstTime = userPreferences.isFirstTime().first()
            _isLoading.value = false
        }
    }

    fun startDestination(): String =
        if (isFirstTime) {
            Screens.GettingStartedScreen.route
        } else {
            Screens.MainScreen.route
        }
}
