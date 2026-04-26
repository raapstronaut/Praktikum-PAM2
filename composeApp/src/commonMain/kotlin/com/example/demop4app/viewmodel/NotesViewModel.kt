package com.example.demop4app.viewmodel

import com.example.demop4app.db.Note
import com.example.demop4app.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class NotesViewModel(
    private val repository: NotesRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortOrder = MutableStateFlow("newest")
    val sortOrder: StateFlow<String> = _sortOrder.asStateFlow()

    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _favoriteUiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val favoriteUiState: StateFlow<NotesUiState> = _favoriteUiState.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

    private val _totalNotesCount = MutableStateFlow(0L)
    val totalNotesCount: StateFlow<Long> = _totalNotesCount.asStateFlow()

    private val _totalFavoritesCount = MutableStateFlow(0L)
    val totalFavoritesCount: StateFlow<Long> = _totalFavoritesCount.asStateFlow()

    init {
        observeNotes()
        observeFavorites()
        refreshCounts()
    }

    private fun observeNotes() {
        scope.launch {
            combine(_searchQuery, _sortOrder) { query, sort ->
                query to sort
            }.flatMapLatest { (query, sort) ->
                if (query.isBlank()) {
                    repository.getAllNotes(sort)
                } else {
                    repository.searchNotes(query, sort)
                }
            }.collect { notes ->
                _uiState.value = if (notes.isEmpty()) {
                    NotesUiState.Empty
                } else {
                    NotesUiState.Content(notes)
                }
                refreshCounts()
            }
        }
    }

    private fun observeFavorites() {
        scope.launch {
            _sortOrder.flatMapLatest { sort ->
                repository.getFavoriteNotes(sort)
            }.collect { notes ->
                _favoriteUiState.value = if (notes.isEmpty()) {
                    NotesUiState.Empty
                } else {
                    NotesUiState.Content(notes)
                }
                refreshCounts()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSortOrder(sort: String) {
        _sortOrder.value = sort
    }

    fun addNote(title: String, content: String) {
        if (title.isBlank() && content.isBlank()) return

        scope.launch {
            repository.insertNote(title, content)
            refreshCounts()
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        scope.launch {
            repository.updateNote(id, title, content)
            refreshCounts()
        }
    }

    fun deleteNote(id: Long) {
        scope.launch {
            repository.deleteNote(id)
            refreshCounts()
        }
    }

    fun toggleFavorite(id: Long, isFavorite: Boolean) {
        scope.launch {
            repository.toggleFavorite(id, isFavorite)
            refreshCounts()
            val currentSelected = _selectedNote.value
            if (currentSelected != null && currentSelected.id == id) {
                _selectedNote.value = repository.getNoteById(id)
            }
        }
    }

    fun selectNote(id: Long) {
        scope.launch {
            _selectedNote.value = repository.getNoteById(id)
        }
    }

    private fun refreshCounts() {
        scope.launch {
            _totalNotesCount.value = repository.getTotalNotesCount()
            _totalFavoritesCount.value = repository.getTotalFavoritesCount()
        }
    }
}