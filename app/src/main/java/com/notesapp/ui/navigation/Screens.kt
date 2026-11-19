package com.notesapp.ui.navigation

sealed class Screens(
    val route: String,
) {
    object MainScreen : Screens("main_screen")
    object GettingStartedScreen : Screens("getting_started_screen")
    object NoteInputScreen : Screens("note_input_screen/{noteId}") {
        fun createRoute(noteId: Long) = "note_input_screen/$noteId"
    }
    object QrCodeScannerScreen : Screens("qr_code_scanner_screen")
    object ExportScreen : Screens("export_screen")
    object QrCodeGeneratorScreen : Screens("qr_code_generator_screen/{noteId}") {
        fun createRoute(noteId: Long) = "qr_code_generator_screen/$noteId"
    }
}
