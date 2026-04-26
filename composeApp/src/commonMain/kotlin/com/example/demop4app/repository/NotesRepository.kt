package com.example.demop4app.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.demop4app.db.Note
import com.example.demop4app.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NotesRepository(
    private val database: NotesDatabase
) {
    private val queries = database.noteQueries

    fun getAllNotes(sortOrder: String) =
        when (sortOrder) {
            "oldest" -> queries.selectAllOldest()
            else -> queries.selectAllNewest()
        }.asFlow().mapToList(Dispatchers.Default)

    fun searchNotes(query: String, sortOrder: String) =
        when (sortOrder) {
            "oldest" -> queries.searchOldest("%$query%", "%$query%")
            else -> queries.searchNewest("%$query%", "%$query%")
        }.asFlow().mapToList(Dispatchers.Default)

    fun getFavoriteNotes(sortOrder: String) =
        when (sortOrder) {
            "oldest" -> queries.selectFavoritesOldest()
            else -> queries.selectFavoritesNewest()
        }.asFlow().mapToList(Dispatchers.Default)

    suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.Default) {
            queries.selectById(id).executeAsOneOrNull()
        }
    }

    suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.Default) {
            queries.insertNote(
                title = title,
                content = content,
                created_at = now,
                updated_at = now
            )
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.Default) {
            queries.updateNote(
                title = title,
                content = content,
                updated_at = now,
                id = id
            )
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.Default) {
            queries.deleteNote(id)
        }
    }

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        withContext(Dispatchers.Default) {
            queries.toggleFavorite(
                is_favorite = if (isFavorite) 1 else 0,
                id = id
            )
        }
    }

    suspend fun getTotalNotesCount(): Long {
        return withContext(Dispatchers.Default) {
            queries.countAllNotes().executeAsOne()
        }
    }

    suspend fun getTotalFavoritesCount(): Long {
        return withContext(Dispatchers.Default) {
            queries.countFavorites().executeAsOne()
        }
    }
}