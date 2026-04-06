package com.example.demop4app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    totalNotes: Int,
    totalFavorites: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Profile")

        Card(modifier = Modifier.padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nama: Muhamad Rafi Ilham")
                Text("NIM: 123140173")
                Text("Total Notes: $totalNotes")
                Text("Total Favorites: $totalFavorites")
                Text("Fitur: Add, Edit, Delete, Favorite, Detail, Botnav.")
            }
        }
    }
}