package com.notesapp.ui.gettingstarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.notesapp.data.UserPreferences

class GettingStartedViewModel(
    private val userPreferences: UserPreferences
): ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            userPreferences.setOnboardingCompleted()
        }
    }
}