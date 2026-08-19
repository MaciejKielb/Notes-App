package com.notesapp.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.notesapp.ui.gettingstarted.GettingStartedScreen
import com.notesapp.ui.splash.SplashViewModel

@Composable
@Suppress("ktlint:standard:function-naming")
fun MainScreen(viewModel: SplashViewModel) {
    val isFirstRun = viewModel.isFirstTime.collectAsState().value

    if (isFirstRun == true) {
        GettingStartedScreen(viewModel)
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Notes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}
