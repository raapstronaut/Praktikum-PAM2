package com.example.demop4app.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.turbine.test
import com.example.demop4app.db.NotesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NotesRepositoryFlowTest {
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
    fun getAllNotes_emitsInsertedNotes() = runTest {
        repository.getAllNotes("newest").test {
            val initial = awaitItem()
            assertTrue(initial.isEmpty())

            repository.insertNote("Flow Note", "Testing with Turbine")

            val updated = awaitItem()
            assertEquals(1, updated.size)
            assertEquals("Flow Note", updated.first().title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun searchNotes_emitsOnlyMatchingNotes() = runTest {
        repository.insertNote("Android UI", "Compose Test")

        repository.searchNotes("Kotlin", "newest").test {
            val initial = awaitItem()
            assertTrue(initial.isEmpty())

            repository.insertNote("Kotlin Testing", "Turbine Flow")

            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Kotlin Testing", result.first().title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getFavoriteNotes_emitsOnlyFavoriteNotes() = runTest {
        repository.insertNote("Favorite Note", "Important")

        val note = repository.getAllNotes("newest")
            .first()
            .first()

        repository.getFavoriteNotes("newest").test {
            val initial = awaitItem()
            assertTrue(initial.isEmpty())

            repository.toggleFavorite(note.id, true)

            val favorites = awaitItem()
            assertEquals(1, favorites.size)
            assertEquals("Favorite Note", favorites.first().title)

            cancelAndIgnoreRemainingEvents()
        }
    }
}