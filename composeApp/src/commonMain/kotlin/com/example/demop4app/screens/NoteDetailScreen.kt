package com.example.demop4app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demop4app.db.Note

@Composable
fun NoteDetailScreen(
    note: Note,
    onBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val isFavorite = note.is_favorite == 1L

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Note Detail",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = note.title,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedButton(
            onClick = onToggleFavorite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite"
            )
            Text(
                text = if (isFavorite) "  Remove from Favorites" else "  Add to Favorites"
            )
        }

        Button(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Note")
        }

        Button(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Note")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}