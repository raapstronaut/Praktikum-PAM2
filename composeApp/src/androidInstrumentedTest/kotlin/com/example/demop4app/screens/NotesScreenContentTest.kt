package com.example.demop4app.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.demop4app.db.Note
import com.example.demop4app.viewmodel.NotesUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesScreenContentTest {
    @get:Rule
    val rule = createComposeRule()

    private fun sampleNote() = Note(
        id = 1L,
        title = "Kotlin Testing",
        content = "Belajar UI Test",
        created_at = 1L,
        updated_at = 1L,
        is_favorite = 0L
    )

    @Test
    fun loadingState_isDisplayed() {
        rule.setContent {
            MaterialTheme {
                NotesScreenContent(
                    uiState = NotesUiState.Loading,
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onNoteClick = {},
                    onToggleFavorite = { _, _ -> },
                    showNetworkStatus = false
                )
            }
        }

        rule.onNodeWithTag(TestTags.LOADING_STATE).assertIsDisplayed()
        rule.onNodeWithText("Loading notes...").assertIsDisplayed()
    }

    @Test
    fun emptyState_isDisplayed() {
        rule.setContent {
            MaterialTheme {
                NotesScreenContent(
                    uiState = NotesUiState.Empty,
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onNoteClick = {},
                    onToggleFavorite = { _, _ -> },
                    showNetworkStatus = false
                )
            }
        }

        rule.onNodeWithTag(TestTags.EMPTY_STATE).assertIsDisplayed()
        rule.onNodeWithText("Belum ada note").assertIsDisplayed()
    }

    @Test
    fun contentState_displaysNotesList() {
        rule.setContent {
            MaterialTheme {
                NotesScreenContent(
                    uiState = NotesUiState.Content(listOf(sampleNote())),
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onNoteClick = {},
                    onToggleFavorite = { _, _ -> },
                    showNetworkStatus = false
                )
            }
        }

        rule.onNodeWithTag(TestTags.NOTES_LIST).assertIsDisplayed()
        rule.onNodeWithTag("${TestTags.NOTE_ITEM}_1").assertIsDisplayed()
        rule.onNodeWithText("Kotlin Testing").assertIsDisplayed()
    }

    @Test
    fun searchInput_acceptsText() {
        rule.setContent {
            MaterialTheme {
                var query by remember { mutableStateOf("") }

                NotesScreenContent(
                    uiState = NotesUiState.Empty,
                    searchQuery = query,
                    onSearchQueryChange = { query = it },
                    onNoteClick = {},
                    onToggleFavorite = { _, _ -> },
                    showNetworkStatus = false
                )
            }
        }

        rule.onNodeWithTag(TestTags.SEARCH_INPUT)
            .performTextInput("Kotlin")

        rule.onNodeWithTag(TestTags.SEARCH_INPUT)
            .assertTextContains("Kotlin")
    }

    @Test
    fun noteItemClick_invokesCallback() {
        var clickedNoteId = 0L

        rule.setContent {
            MaterialTheme {
                NotesScreenContent(
                    uiState = NotesUiState.Content(listOf(sampleNote())),
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onNoteClick = { clickedNoteId = it },
                    onToggleFavorite = { _, _ -> },
                    showNetworkStatus = false
                )
            }
        }

        rule.onNodeWithTag("${TestTags.NOTE_ITEM}_1")
            .performClick()

        assertEquals(1L, clickedNoteId)
    }
}