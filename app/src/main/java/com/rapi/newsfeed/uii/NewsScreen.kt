package com.rapi.newsfeed.uii

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rapi.newsfeed.model.Category
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewsScreen(vm: NewsViewModel) {
    val feed by vm.newsUiStream.collectAsState()
    val readCount by vm.readCount.collectAsState()
    val selected by vm.selectedCategory.collectAsState()
    val detailState by vm.detailState.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collectLatest { println("EVENT: $it") }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("News Feed Simulator", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(
                onClick = { vm.setCategory(null) },
                label = { Text("ALL") }
            )
            Category.entries.forEach { c ->
                AssistChip(
                    onClick = { vm.setCategory(c) },
                    label = { Text(c.name) }
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        Text("Read count: $readCount")

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(feed, key = { it.id }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            vm.markAsRead()
                            vm.loadDetail(item.id)
                        }
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(item.headline, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(item.badge, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        DetailBox(detailState)
    }
}

@Composable
private fun DetailBox(state: DetailUiState) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text("Detail", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            when (state) {
                DetailUiState.Idle -> Text("Klik berita untuk melihat detail.")
                DetailUiState.Loading -> CircularProgressIndicator()
                is DetailUiState.Success -> {
                    Text("Author: ${state.detail.author}")
                    Spacer(Modifier.height(4.dp))
                    Text(state.detail.content)
                }
                is DetailUiState.Error -> Text("Error: ${state.message}")
            }
        }
    }
}