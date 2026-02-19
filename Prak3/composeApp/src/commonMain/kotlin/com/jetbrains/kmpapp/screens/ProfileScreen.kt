package com.jetbrains.kmpapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.profil


@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileCard {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                ProfileHeader(
                    name = "Muhamad Rafi Ilham",
                    subtitle = "123140173"
                )

                Text(
                    text = "Saya adalah mahasiswa ITERA yang sedang mengambil mata kuliah PAM",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF444444)
                )

                Divider()

                InfoItem(iconText = "✉️", label = "Email", value = "muhamad.123140173@student.itera.ac.id")
                InfoItem(iconText = "📞", label = "Phone", value = "+62 813-1924-6278")
                InfoItem(iconText = "📍", label = "Location", value = "Metro")

                Spacer(Modifier.height(8.dp))

            }
        }
    }
}

/** Reusable #1 */
@Composable
fun ProfileHeader(name: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAEAEA)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.profil),
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(text = name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = subtitle, color = Color.Gray)
        }
    }
}

/** Reusable #2 */
@Composable
fun InfoItem(iconText: String, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(iconText)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/** Reusable #3 */
@Composable
fun ProfileCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(Modifier.padding(16.dp)) { content() }
    }
}
