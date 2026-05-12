package com.example.demop4app.viewmodel

import com.example.demop4app.db.Note
import com.example.demop4app.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class NotesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val repository = mockk<NotesRepository>()

    private fun sampleNote() = Note(
        id = 1L,
        title = "Test Note",
        content = "Test Content",
        created_at = 1L,
        updated_at = 1L,
        is_favorite = 0L
    )

    private fun stubRepository(notes: List<Note> = listOf(sampleNote())) {
        every { repository.getAllNotes(any()) } returns flowOf(notes)
        every { repository.searchNotes(any(), any()) } returns flowOf(notes)
        every { repository.getFavoriteNotes(any()) } returns flowOf(emptyList())

        coEvery { repository.getTotalNotesCount() } returns notes.size.toLong()
        coEvery { repository.getTotalFavoritesCount() } returns 0L
        coEvery { repository.insertNote(any(), any()) } returns Unit
        coEvery { repository.updateNote(any(), any(), any()) } returns Unit
        coEvery { repository.deleteNote(any()) } returns Unit
        coEvery { repository.toggleFavorite(any(), any()) } returns Unit
        coEvery { repository.getNoteById(any()) } returns sampleNote()
    }

    @Test
    fun initialState_becomesContent_whenRepositoryHasNotes() = runTest(testDispatcher) {
        stubRepository(notes = listOf(sampleNote()))

        val viewModel = NotesViewModel(repository, testDispatcher)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertIs<NotesUiState.Content>(state)
        assertEquals(1, state.notes.size)
        assertEquals("Test Note", state.notes.first().title)

        viewModel.clear()
    }

    @Test
    fun initialState_becomesEmpty_whenRepositoryReturnsEmptyList() = runTest(testDispatcher) {
        stubRepository(notes = emptyList())

        val viewModel = NotesViewModel(repository, testDispatcher)
        advanceUntilIdle()

        assertIs<NotesUiState.Empty>(viewModel.uiState.value)

        viewModel.clear()
    }

    @Test
    fun addNote_callsRepositoryInsertNote() = runTest(testDispatcher) {
        stubRepository(notes = emptyList())

        val viewModel = NotesViewModel(repository, testDispatcher)
        viewModel.addNote("New Note", "New Content")
        advanceUntilIdle()

        coVerify {
            repository.insertNote("New Note", "New Content")
        }

        viewModel.clear()
    }

    @Test
    fun addNote_withBlankTitleAndContent_doesNotCallRepository() = runTest(testDispatcher) {
        stubRepository(notes = emptyList())

        val viewModel = NotesViewModel(repository, testDispatcher)
        viewModel.addNote("", "")
        advanceUntilIdle()

        coVerify(exactly = 0) {
            repository.insertNote(any(), any())
        }

        viewModel.clear()
    }

    @Test
    fun deleteNote_callsRepositoryDeleteNote() = runTest(testDispatcher) {
        stubRepository(notes = listOf(sampleNote()))

        val viewModel = NotesViewModel(repository, testDispatcher)
        viewModel.deleteNote(1L)
        advanceUntilIdle()

        coVerify {
            repository.deleteNote(1L)
        }

        viewModel.clear()
    }

    @Test
    fun updateSearchQuery_changesSearchQueryValue() = runTest(testDispatcher) {
        stubRepository(notes = listOf(sampleNote()))

        val viewModel = NotesViewModel(repository, testDispatcher)
        viewModel.updateSearchQuery("Kotlin")
        advanceUntilIdle()

        assertEquals("Kotlin", viewModel.searchQuery.value)

        viewModel.clear()
    }
}