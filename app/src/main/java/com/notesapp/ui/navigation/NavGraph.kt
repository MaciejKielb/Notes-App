package com.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notesapp.ui.gettingstarted.GettingStartedScreen
import com.notesapp.ui.mainscreen.MainScreen
import com.notesapp.ui.notes.NoteInputScreen
import com.notesapp.ui.notes.NoteInputViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screens.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }
        composable(Screens.GettingStartedScreen.route) {
            GettingStartedScreen(
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable(
            route = Screens.NoteInputScreen.route,
            arguments = listOf(
                navArgumednt("noteId") { type = NavType.LongType }
            ),
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            val noteInputViewModel = koinViewModel<NoteInputViewModel>()
            NoteInputScreen(
                noteId = noteId,
                viewModel = noteInputViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}
