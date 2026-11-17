package com.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notesapp.qrcodescanner.QrCodeScreen
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
                viewModel = koinViewModel(),
                onAddNoteClick = {
                    navController.navigate(Screens.NoteInputScreen.createRoute(0L))
                },
                onEditNoteClick = { noteId ->
                    navController.navigate(Screens.NoteInputScreen.createRoute(noteId))
                },
                openQrCodeScanner = { navController.navigate(Screens.QrCodeScannerScreen.route)
                },
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
                navArgument("noteId") { type = NavType.LongType }
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
        composable (
            route = Screens.QrCodeScannerScreen.route
        ) {
            QrCodeScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
