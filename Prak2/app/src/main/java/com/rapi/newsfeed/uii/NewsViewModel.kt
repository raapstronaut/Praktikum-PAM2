package com.rapi.newsfeed.uii

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rapi.newsfeed.data.NewsRepository
import com.rapi.newsfeed.model.Category
import com.rapi.newsfeed.model.NewsDetail
import com.rapi.newsfeed.model.NewsItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data object Idle : DetailUiState()
    data object Loading : DetailUiState()
    data class Success(val detail: NewsDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class NewsViewModel(
    private val repo: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events.asSharedFlow()

    private val _detailState = MutableStateFlow<DetailUiState>(DetailUiState.Idle)
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()

    private val rawStream = repo.newsStream()
        .catch { e -> _events.emit("Stream error: ${e.message}") }

    val newsUiStream: StateFlow<List<NewsUiItem>> =
        rawStream
            .combine(selectedCategory) { item: NewsItem, cat: Category? -> item to cat }
            .filter { (item, cat) -> cat == null || item.category == cat }
            .map { (item, _) ->
                NewsUiItem(
                    id = item.id,
                    headline = item.title,
                    badge = "ID:${item.id} • ${item.category}",
                    category = item.category
                )
            }
            .scan(emptyList<NewsUiItem>()) { acc: List<NewsUiItem>, newItem: NewsUiItem ->
                (listOf(newItem) + acc).take(30)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList<NewsUiItem>() // ✅ penting
            )

    fun setCategory(category: Category?) {
        _selectedCategory.value = category
    }

    fun markAsRead() {
        _readCount.value = _readCount.value + 1
    }

    fun loadDetail(newsId: Int) {
        viewModelScope.launch {
            _detailState.value = DetailUiState.Loading
            try {
                val detail = repo.fetchDetail(newsId)
                _detailState.value = DetailUiState.Success(detail)
            } catch (e: Exception) {
                _detailState.value = DetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
