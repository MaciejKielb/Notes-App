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
    private val isFirstTime = MutableStateFlow<Boolean?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            userPreferences.isFirstTime().collect { firstRun ->
                isFirstTime.value = firstRun
                _isLoading.value = false
            }
        }
    }

    fun isFirstLaunch(): Boolean = isFirstTime.value == true
}
