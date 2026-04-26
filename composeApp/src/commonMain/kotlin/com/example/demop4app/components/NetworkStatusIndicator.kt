package com.example.demop4app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demop4app.platform.NetworkMonitor
import org.koin.compose.koinInject

@Composable
fun NetworkStatusIndicator() {
    val networkMonitor: NetworkMonitor = koinInject()
    val isConnected by networkMonitor.observeConnectivity().collectAsState(initial = true)

    AnimatedVisibility(visible = !isConnected) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "No Internet Connection",
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}