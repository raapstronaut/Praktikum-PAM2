package com.rapi.newsreader.viewmodel

import com.rapi.newsreader.model.Article
import com.rapi.newsreader.repository.NewsRepository
import com.rapi.newsreader.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        scope.launch {
            _uiState.value = UiState.Loading

            delay(1500)

            repository.getTopHeadlines()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Failed to load news"
                    )
                }
        }
    }

    fun refresh() {
        scope.launch {
            _isRefreshing.value = true

            repository.getTopHeadlines()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Failed to refresh news"
                    )
                }

            _isRefreshing.value = false
        }
    }
}