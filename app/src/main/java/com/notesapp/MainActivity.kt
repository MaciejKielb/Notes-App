package com.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.notesapp.ui.navigation.Screens
import com.notesapp.ui.navigation.SetupNavGraph
import com.notesapp.ui.splash.SplashViewModel
import com.notesapp.ui.theme.NotesAppTheme
import com.notesapp.ui.util.IntentParameters
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }

        enableEdgeToEdge()

        lifecycleScope.launch {
            viewModel.isLoading.filter { isLoading -> !isLoading }.first()

            intent.putExtra(
                IntentParameters.IS_FIRST_TIME,
                viewModel.isFirstLaunch(),
            )

            val startDestination: Screens =
                when (intent.getBooleanExtra(IntentParameters.IS_FIRST_TIME, false)) {
                    true -> Screens.GettingStartedScreen
                    else -> Screens.MainScreen
                }

            setContent {
                NotesAppTheme {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        startDestination = startDestination.route,
                    )
                }
            }
        }
    }
}
