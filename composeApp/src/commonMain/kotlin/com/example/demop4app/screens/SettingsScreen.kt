package com.example.demop4app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demop4app.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val theme by viewModel.theme.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Current: $theme",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(onClick = { viewModel.updateTheme("light") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Light")
                }
                Button(onClick = { viewModel.updateTheme("dark") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Dark")
                }
                Button(onClick = { viewModel.updateTheme("system") }, modifier = Modifier.fillMaxWidth()) {
                    Text("System")
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Sort Order",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Current: $sortOrder",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(onClick = { viewModel.updateSortOrder("newest") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Newest First")
                }
                Button(onClick = { viewModel.updateSortOrder("oldest") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Oldest First")
                }
            }
        }
    }
}