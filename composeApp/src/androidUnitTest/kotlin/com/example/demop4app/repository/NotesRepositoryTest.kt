package com.example.demop4app.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.demop4app.db.NotesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NotesRepositoryTest {
    private lateinit var driver: JdbcSqliteDriver
    private lateinit var database: NotesDatabase
    private lateinit var repository: NotesRepository

    @BeforeTest
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NotesDatabase.Schema.create(driver)
        database = NotesDatabase(driver)
        repository = SqlDelightNotesRepository(database)
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun insertNote_increasesTotalNotesCount() = runTest {
        repository.insertNote("Belajar KMP", "Testing repository")

        val count = repository.getTotalNotesCount()

        assertEquals(1L, count)
    }

    @Test
    fun getNoteById_returnsInsertedNote() = runTest {
        repository.insertNote("Judul", "Konten")
        val insertedNote = repository.getAllNotes("newest").first().first()

        val result = repository.getNoteById(insertedNote.id)

        assertNotNull(result)
        assertEquals("Judul", result.title)
        assertEquals("Konten", result.content)
    }

    @Test
    fun updateNote_changesTitleAndContent() = runTest {
        repository.insertNote("Lama", "Konten lama")
        val insertedNote = repository.getAllNotes("newest").first().first()

        repository.updateNote(insertedNote.id, "Baru", "Konten baru")
        val updatedNote = repository.getNoteById(insertedNote.id)

        assertNotNull(updatedNote)
        assertEquals("Baru", updatedNote.title)
        assertEquals("Konten baru", updatedNote.content)
    }

    @Test
    fun deleteNote_removesNoteFromDatabase() = runTest {
        repository.insertNote("Hapus", "Catatan ini akan dihapus")
        val insertedNote = repository.getAllNotes("newest").first().first()

        repository.deleteNote(insertedNote.id)
        val deletedNote = repository.getNoteById(insertedNote.id)

        assertNull(deletedNote)
        assertEquals(0L, repository.getTotalNotesCount())
    }

    @Test
    fun toggleFavorite_marksNoteAsFavorite() = runTest {
        repository.insertNote("Favorit", "Catatan favorit")
        val insertedNote = repository.getAllNotes("newest").first().first()

        repository.toggleFavorite(insertedNote.id, true)

        val favoriteCount = repository.getTotalFavoritesCount()
        val updatedNote = repository.getNoteById(insertedNote.id)

        assertEquals(1L, favoriteCount)
        assertEquals(1L, updatedNote?.is_favorite)
    }

    @Test
    fun searchNotes_returnsMatchingNotes() = runTest {
        repository.insertNote("Kotlin", "Belajar testing")
        repository.insertNote("Android", "Belajar UI")

        val result = repository.searchNotes("Kotlin", "newest").first()

        assertEquals(1, result.size)
        assertEquals("Kotlin", result.first().title)
    }
}