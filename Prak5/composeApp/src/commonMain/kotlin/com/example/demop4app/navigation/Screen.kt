package com.example.demop4app.navigation

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object Settings : Screen("settings")
    object AddNote : Screen("add_note")

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }

    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Long) = "edit_note/$noteId"
    }
}