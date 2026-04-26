package com.example.demop4app.viewmodel

import com.example.demop4app.db.Note

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Content(val notes: List<Note>) : NotesUiState()
}