package com.example.demop4app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.demop4app.data.model.Note

class NotesViewModel : ViewModel() {

    private var nextId = 1

    private val _notes = mutableStateListOf<Note>()

    val notes: List<Note> get() = _notes

    fun getNoteById(noteId: Int): Note? {
        return _notes.find { it.id == noteId }
    }

    fun addNote(title: String, content: String) {
        if (title.isBlank() && content.isBlank()) return
        _notes.add(
            Note(
                id = nextId++,
                title = title,
                content = content,
                isFavorite = false
            )
        )
    }

    fun updateNote(noteId: Int, title: String, content: String) {
        val index = _notes.indexOfFirst { it.id == noteId }
        if (index != -1) {
            val oldNote = _notes[index]
            _notes[index] = oldNote.copy(
                title = title,
                content = content
            )
        }
    }

    fun deleteNote(noteId: Int) {
        _notes.removeAll { it.id == noteId }
    }

    fun toggleFavorite(noteId: Int) {
        val index = _notes.indexOfFirst { it.id == noteId }
        if (index != -1) {
            val oldNote = _notes[index]
            _notes[index] = oldNote.copy(
                isFavorite = !oldNote.isFavorite
            )
        }
    }

    fun getFavoriteNotes(): List<Note> {
        return _notes.filter { it.isFavorite }
    }
}