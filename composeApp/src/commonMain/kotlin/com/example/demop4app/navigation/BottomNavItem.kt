package com.example.demop4app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Notes : BottomNavItem(
        route = Screen.Notes.route,
        icon = Icons.Default.Home,
        label = "Notes"
    )

    object Settings : BottomNavItem(
        route = Screen.Settings.route,
        icon = Icons.Default.Settings,
        label = "Settings"
    )
}