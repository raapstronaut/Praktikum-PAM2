package com.example.demop4app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.demop4app.db.DatabaseDriverFactory
import com.example.demop4app.db.DatabaseProvider
import com.example.demop4app.navigation.AppNavigation
import com.example.demop4app.repository.NotesRepository
import com.example.demop4app.settings.SettingsFactory
import com.example.demop4app.settings.SettingsRepository
import com.example.demop4app.settings.SettingsViewModel
import com.example.demop4app.viewmodel.NotesViewModel

@Composable
fun App(
    driverFactory: DatabaseDriverFactory,
    settingsFactory: SettingsFactory
) {
    val database = remember { DatabaseProvider.getDatabase(driverFactory) }
    val notesRepository = remember { NotesRepository(database) }
    val notesViewModel = remember { NotesViewModel(notesRepository) }

    val settings = remember { settingsFactory.createSettings() }
    val settingsRepository = remember { SettingsRepository(settings) }
    val settingsViewModel = remember { SettingsViewModel(settingsRepository) }

    val sortOrder by settingsViewModel.sortOrder.collectAsState()
    val theme by settingsViewModel.theme.collectAsState()

    LaunchedEffect(sortOrder) {
        notesViewModel.updateSortOrder(sortOrder)
    }

    val useDarkTheme = when (theme) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        AppNavigation(
            notesViewModel = notesViewModel,
            settingsViewModel = settingsViewModel
        )
    }
}