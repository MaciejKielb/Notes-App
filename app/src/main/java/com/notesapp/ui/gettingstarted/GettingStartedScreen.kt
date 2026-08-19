package com.notesapp.ui.gettingstarted

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.notesapp.ui.splash.SplashViewModel

@Composable
@Suppress("ktlint:standard:function-naming")
fun GettingStartedScreen(viewModel: SplashViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Welcome to Notes APP",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            onClick = { viewModel.completeOnboarding() },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text("I finished onboarding")
        }
    }
}
