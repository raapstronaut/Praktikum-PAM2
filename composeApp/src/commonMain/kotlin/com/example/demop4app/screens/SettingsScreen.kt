package com.example.demop4app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demop4app.platform.DeviceInfo
import com.example.demop4app.settings.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val theme by viewModel.theme.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()
    val deviceInfo: DeviceInfo = koinInject()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(26.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "  Settings",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Device Info",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Device: ${deviceInfo.getDeviceName()}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "OS: ${deviceInfo.getOsVersion()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "App Version: ${deviceInfo.getAppVersion()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium
                )

                ThemeOption(
                    title = "Light",
                    selected = theme == "light",
                    onClick = { viewModel.updateTheme("light") }
                )

                ThemeOption(
                    title = "Dark",
                    selected = theme == "dark",
                    onClick = { viewModel.updateTheme("dark") }
                )

                ThemeOption(
                    title = "System",
                    selected = theme == "system",
                    onClick = { viewModel.updateTheme("system") }
                )
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Sort Order",
                    style = MaterialTheme.typography.titleMedium
                )

                SortOption(
                    title = "Newest First",
                    subtitle = "Catatan terbaru tampil di atas",
                    selected = sortOrder == "newest",
                    onClick = { viewModel.updateSortOrder("newest") }
                )

                SortOption(
                    title = "Oldest First",
                    subtitle = "Catatan terlama tampil di atas",
                    selected = sortOrder == "oldest",
                    onClick = { viewModel.updateSortOrder("oldest") }
                )
            }
        }
    }
}

@Composable
private fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick
            ),
        tonalElevation = if (selected) 3.dp else 0.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun SortOption(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick
            ),
        tonalElevation = if (selected) 3.dp else 0.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}