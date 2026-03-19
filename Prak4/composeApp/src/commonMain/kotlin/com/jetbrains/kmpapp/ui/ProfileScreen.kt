package com.jetbrains.kmpapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.viewmodel.ProfileViewModel
import org.jetbrains.compose.resources.painterResource
import prak4.composeapp.generated.resources.Res
import prak4.composeapp.generated.resources.profil

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val profile = uiState.profile

    if (uiState.isEditing) {
        EditProfileForm(
            currentName = profile.name,
            currentNim = profile.nim,
            currentBio = profile.bio,
            currentEmail = profile.email,
            currentPhone = profile.phone,
            currentLocation = profile.location,
            onSave = { newName, newNim, newBio, newEmail, newPhone, newLocation ->
                viewModel.saveProfile(
                    newName,
                    newNim,
                    newBio,
                    newEmail,
                    newPhone,
                    newLocation
                )
            },
            onCancel = {
                viewModel.setEditing(false)
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 32.dp, start = 0.dp, end = 0.dp, bottom = 32.dp)
        ) {
            ProfileCard(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ProfileHeader(
                            name = profile.name,
                            subtitle = profile.nim
                        )

                        Text(
                            text = profile.bio,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                            ),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.fillMaxWidth()
                        )

                        HorizontalDivider()

                        InfoItem("✉️", "Email", profile.email)
                        InfoItem("📞", "Phone", profile.phone)
                        InfoItem("📍", "Location", profile.location)
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Dark Mode",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )

                            Switch(
                                checked = uiState.isDarkMode,
                                onCheckedChange = {
                                    viewModel.toggleDarkMode()
                                }
                            )
                        }

                        Button(
                            onClick = { viewModel.setEditing(true) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Edit Profile")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAEAEA)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.profil),
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun InfoItem(iconText: String, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = iconText)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}