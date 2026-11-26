package com.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notesapp.qrcode.CreateQrCodeGeneratorScreen
import com.notesapp.qrcode.QrCodeScannerScreen
import com.notesapp.qrcode.QrCodeViewModel
import com.notesapp.ui.exportscreen.ExportScreen
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
    val navigateBack: () -> Unit = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

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
                openQrCodeScanner = {
                    navController.navigate(Screens.QrCodeScannerScreen.route)
                },
                openExportScreen = {
                    navController.navigate(Screens.ExportScreen.route)
                },
                openQrCodeGenerator = { noteId ->
                    navController.navigate(Screens.QrCodeGeneratorScreen.createRoute(noteId))
                }
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
                onBackClick = navigateBack,
            )
        }
        composable (Screens.QrCodeScannerScreen.route) {
            QrCodeScannerScreen(
                onBackClick = navigateBack
            )
        }
        composable(Screens.ExportScreen.route) {
            ExportScreen(
                onBackClick = navigateBack,
            )
        }
        composable(
            route = Screens.QrCodeGeneratorScreen.route,
            arguments = listOf(
                navArgument("noteId") { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            CreateQrCodeGeneratorScreen(
                noteId = noteId,
                viewModel = koinViewModel<QrCodeViewModel>(),
                onBackClick = navigateBack,
            )
        }
    }
}
