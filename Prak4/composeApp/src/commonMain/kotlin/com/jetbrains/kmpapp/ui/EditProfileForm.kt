package com.jetbrains.kmpapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.ui.components.ProfileTextField
import androidx.compose.foundation.background

@Composable
fun EditProfileForm(
    currentName: String,
    currentNim: String,
    currentBio: String,
    currentEmail: String,
    currentPhone: String,
    currentLocation: String,
    onSave: (String, String, String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var nim by remember { mutableStateOf(currentNim) }
    var bio by remember { mutableStateOf(currentBio) }
    var email by remember { mutableStateOf(currentEmail) }
    var phone by remember { mutableStateOf(currentPhone) }
    var location by remember { mutableStateOf(currentLocation) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineSmall
        )

        ProfileTextField(
            label = "Nama",
            value = name,
            onValueChange = { name = it }
        )

        ProfileTextField(
            label = "NIM",
            value = nim,
            onValueChange = { nim = it }
        )

        ProfileTextField(
            label = "Bio",
            value = bio,
            onValueChange = { bio = it },
            singleLine = false
        )

        ProfileTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it }
        )

        ProfileTextField(
            label = "No HP",
            value = phone,
            onValueChange = { phone = it }
        )

        ProfileTextField(
            label = "Lokasi",
            value = location,
            onValueChange = { location = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    onSave(name, nim, bio, email, phone, location)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
        }
    }
}