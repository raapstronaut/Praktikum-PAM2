package com.jetbrains.kmpapp

import androidx.compose.runtime.*
import com.jetbrains.kmpapp.ui.ProfileScreen
import com.jetbrains.kmpapp.ui.theme.MyProfileTheme
import com.jetbrains.kmpapp.viewmodel.ProfileViewModel

@Composable
fun App() {
    val viewModel = remember { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    MyProfileTheme(
        darkTheme = uiState.isDarkMode
    ) {
        ProfileScreen(viewModel = viewModel)
    }
}