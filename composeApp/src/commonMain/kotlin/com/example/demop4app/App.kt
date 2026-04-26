package com.example.demop4app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.demop4app.navigation.AppNavigation
import com.example.demop4app.settings.SettingsViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val settingsViewModel: SettingsViewModel = koinInject()
        val theme by settingsViewModel.theme.collectAsState()

        val useDarkTheme = when (theme) {
            "light" -> false
            "dark" -> true
            else -> isSystemInDarkTheme()
        }

        MaterialTheme(
            colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            AppNavigation()
        }
    }
}