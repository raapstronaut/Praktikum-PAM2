package com.jetbrains.kmpapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun MyProfileTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}