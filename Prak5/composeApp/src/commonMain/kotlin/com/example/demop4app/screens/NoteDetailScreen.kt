package com.example.demop4app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demop4app.data.model.Note

@Composable
fun NoteDetailScreen(
    note: Note,
    onBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Note Detail")
        Text("ID: ${note.id}")
        Text("Title: ${note.title}")
        Text("Content: ${note.content}")
        Text("Favorite: ${if (note.isFavorite) "Yes" else "No"}")

        Button(onClick = onEditClick) {
            Text("Edit Note")
        }

        Button(onClick = onToggleFavorite) {
            Text(if (note.isFavorite) "Remove Favorite" else "Add to Favorite")
        }

        Button(onClick = onDeleteClick) {
            Text("Delete Note")
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}