package com.example.demop4app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.demop4app.components.NetworkStatusIndicator
import com.example.demop4app.db.Note
import com.example.demop4app.viewmodel.NotesUiState
import com.example.demop4app.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    NotesScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onNoteClick = onNoteClick,
        onToggleFavorite = viewModel::toggleFavorite,
        showNetworkStatus = true
    )
}

@Composable
fun NotesScreenContent(
    uiState: NotesUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNoteClick: (Long) -> Unit,
    onToggleFavorite: (Long, Boolean) -> Unit,
    showNetworkStatus: Boolean = true
) {
    when (uiState) {
        is NotesUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(TestTags.LOADING_STATE),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()

                    Text(
                        text = "Loading notes...",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Mengambil data dari database...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        is NotesUiState.Empty -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag(TestTags.EMPTY_STATE),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (showNetworkStatus) {
                    NetworkStatusIndicator()
                }

                NotesHeader()

                SearchField(
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange
                )

                EmptyNotesContent(searchQuery = searchQuery)
            }
        }

        is NotesUiState.Content -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (showNetworkStatus) {
                    NetworkStatusIndicator()
                }

                NotesHeader()

                SearchField(
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange
                )

                LazyColumn(
                    modifier = Modifier.testTag(TestTags.NOTES_LIST),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notes) { note ->
                        NoteItem(
                            note = note,
                            onNoteClick = onNoteClick,
                            onToggleFavorite = onToggleFavorite
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotesHeader() {
    Text(
        text = "My Notes",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.NOTES_TITLE),
        textAlign = TextAlign.Center
    )

    Text(
        text = "Kelola catatanmu dengan mudah",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun SearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = { Text("Search notes") },
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.SEARCH_INPUT),
        singleLine = true
    )
}

@Composable
private fun EmptyNotesContent(
    searchQuery: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (searchQuery.isBlank()) "📝" else "🔍",
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = if (searchQuery.isBlank()) {
                    "Belum ada note"
                } else {
                    "Hasil tidak ditemukan"
                },
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (searchQuery.isBlank()) {
                    "Tekan tombol tambah untuk membuat catatan pertama."
                } else {
                    "Coba gunakan kata kunci lain."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    onNoteClick: (Long) -> Unit,
    onToggleFavorite: (Long, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("${TestTags.NOTE_ITEM}_${note.id}")
            .clickable { onNoteClick(note.id) },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                modifier = Modifier.testTag("${TestTags.FAVORITE_BUTTON}_${note.id}"),
                onClick = {
                    onToggleFavorite(
                        note.id,
                        note.is_favorite != 1L
                    )
                }
            ) {
                Icon(
                    imageVector = if (note.is_favorite == 1L) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = "Toggle Favorite",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}