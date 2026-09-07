package com.notesapp.ui.gettingstarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.UserPreferences
import kotlinx.coroutines.launch

class GettingStartedViewModel(
    private val userPreferences: UserPreferences,
) : ViewModel() {
    fun completeOnboarding() {
        viewModelScope.launch {
            userPreferences.setOnboardingCompleted()
        }
    }
}
