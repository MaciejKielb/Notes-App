package com.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.notesapp.ui.gettingstarted.GettingStartedScreen
import com.notesapp.ui.mainscreen.MainScreen
import com.notesapp.ui.splash.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: SplashViewModel,
) {
    if (viewModel.isLoading.collectAsState().value) {
        return
    }

    NavHost(
        navController = navController,
        startDestination = viewModel.startDestination(),
    ) {
        composable(Screens.MainScreen.route) {
            MainScreen()
        }
        composable(Screens.GettingStartedScreen.route) {
            GettingStartedScreen(
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
    }
}
