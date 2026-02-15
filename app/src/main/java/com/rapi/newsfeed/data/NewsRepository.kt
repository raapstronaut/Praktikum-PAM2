package com.rapi.newsfeed.data

import com.rapi.newsfeed.model.Category
import com.rapi.newsfeed.model.NewsDetail
import com.rapi.newsfeed.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class NewsRepository {

    // (1) Flow yang mensimulasikan berita baru tiap 2 detik
    fun newsStream(): Flow<NewsItem> = flow {
        var id = 1
        while (true) {
            delay(2000)
            emit(generateNews(id++))
        }
    }.flowOn(Dispatchers.Default)

    // (5) Coroutine async ambil detail berita
    suspend fun fetchDetail(newsId: Int): NewsDetail = withContext(Dispatchers.IO) {
        delay(800) // simulasi network
        NewsDetail(
            id = newsId,
            content = "Ini detail untuk berita #$newsId (simulasi).",
            author = listOf("Rafi", "Dewi", "Budi", "Ann").random()
        )
    }

    private fun generateNews(id: Int): NewsItem {
        val category = Category.entries.random()
        val title = when (category) {
            Category.TECH -> "Tech Update #$id"
            Category.SPORT -> "Sport News #$id"
            Category.BUSINESS -> "Business Insight #$id"
            Category.ENTERTAINMENT -> "Entertainment Buzz #$id"
        }
        return NewsItem(
            id = id,
            title = title,
            category = category,
            timestampMillis = System.currentTimeMillis()
        )
    }
}
