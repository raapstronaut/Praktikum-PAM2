package com.rapi.newsreader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.rapi.newsreader.model.Article
import com.rapi.newsreader.network.HttpClientFactory
import com.rapi.newsreader.repository.NewsRepository
import com.rapi.newsreader.screen.NewsDetailScreen
import com.rapi.newsreader.screen.NewsListScreen
import com.rapi.newsreader.viewmodel.NewsViewModel

@Composable
fun App() {
    MaterialTheme {
        val client = remember { HttpClientFactory.create() }
        val repository = remember { NewsRepository(client) }
        val viewModel = remember { NewsViewModel(repository) }

        var selectedArticle by remember { mutableStateOf<Article?>(null) }

        if (selectedArticle == null) {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    selectedArticle = article
                }
            )
        } else {
            NewsDetailScreen(
                article = selectedArticle!!,
                onBack = {
                    selectedArticle = null
                }
            )
        }
    }
}