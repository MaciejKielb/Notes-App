package com.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable(Screens.NoteInputScreen.route) {
            val noteInputViewModel = koinViewModel<NoteInputViewModel>()
            NoteInputScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = { title, text ->
                    if(title.isBlank() || text.isBlank()) return@NoteInputScreen
                    noteInputViewModel.saveNote(
                        note = com.notesapp.data.Notes(
                            title = title.trim(),
                            text = text.trim(),
                            createdOn = System.currentTimeMillis(),
                            updatedOn = System.currentTimeMillis(),
                        )
                    )
                }
            )
        }
    }
}
