package com.example.demop4app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.demop4app.navigation.AppNavigation

@Composable
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}