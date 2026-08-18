package com.notesapp.ui.mainscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.notesapp.ui.splash.SplashViewModel
import androidx.compose.runtime.collectAsState
import com.notesapp.ui.gettingstarted.GettingStartedScreen

@Composable
fun MainScreen(
    viewModel: SplashViewModel
){
    val isFirstRun = viewModel.isFirstTime.collectAsState().value

    if(isFirstRun == true) {
        GettingStartedScreen(viewModel)
    } else {
        Text("Notes")
    }
}