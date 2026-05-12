package com.example.demop4app.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.demop4app.db.Note
import com.example.demop4app.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

interface NotesRepository {
    fun getAllNotes(sortOrder: String): Flow<List<Note>>
    fun searchNotes(query: String, sortOrder: String): Flow<List<Note>>
    fun getFavoriteNotes(sortOrder: String): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(title: String, content: String)
    suspend fun updateNote(id: Long, title: String, content: String)
    suspend fun deleteNote(id: Long)
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)
    suspend fun getTotalNotesCount(): Long
    suspend fun getTotalFavoritesCount(): Long
}

class SqlDelightNotesRepository(
    private val database: NotesDatabase
) : NotesRepository {

    private val queries = database.noteQueries

    override fun getAllNotes(sortOrder: String): Flow<List<Note>> =
        when (sortOrder) {
            "oldest" -> queries.selectAllOldest()
            else -> queries.selectAllNewest()
        }.asFlow().mapToList(Dispatchers.Default)

    override fun searchNotes(query: String, sortOrder: String): Flow<List<Note>> =
        when (sortOrder) {
            "oldest" -> queries.searchOldest("%$query%", "%$query%")
            else -> queries.searchNewest("%$query%", "%$query%")
        }.asFlow().mapToList(Dispatchers.Default)

    override fun getFavoriteNotes(sortOrder: String): Flow<List<Note>> =
        when (sortOrder) {
            "oldest" -> queries.selectFavoritesOldest()
            else -> queries.selectFavoritesNewest()
        }.asFlow().mapToList(Dispatchers.Default)

    override suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.Default) {
            queries.selectById(id).executeAsOneOrNull()
        }
    }

    override suspend fun insertNote(title: String, content: String) {
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

    override suspend fun updateNote(id: Long, title: String, content: String) {
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

    override suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.Default) {
            queries.deleteNote(id)
        }
    }

    override suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        withContext(Dispatchers.Default) {
            queries.toggleFavorite(
                is_favorite = if (isFavorite) 1 else 0,
                id = id
            )
        }
    }

    override suspend fun getTotalNotesCount(): Long {
        return withContext(Dispatchers.Default) {
            queries.countAllNotes().executeAsOne()
        }
    }

    override suspend fun getTotalFavoritesCount(): Long {
        return withContext(Dispatchers.Default) {
            queries.countFavorites().executeAsOne()
        }
    }
}