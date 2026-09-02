package com.notesapp.ui.navigation

sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object GettingStartedScreen : Screens("getting_started_screen")
}