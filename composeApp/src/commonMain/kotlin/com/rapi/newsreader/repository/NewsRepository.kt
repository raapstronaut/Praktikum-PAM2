package com.rapi.newsreader.repository

import com.rapi.newsreader.model.Article
import com.rapi.newsreader.model.NewsResponse
import com.rapi.newsreader.model.toArticle
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NewsRepository(
    private val client: HttpClient
) {
    private val apiKey = "138e07f08dfa4a9081c54fedffb880cb"
    private val baseUrl = "https://newsapi.org/v2"

    suspend fun getTopHeadlines(): Result<List<Article>> {
        return try {
            val response: NewsResponse = client.get(
                "$baseUrl/top-headlines?country=us&category=technology&apiKey=$apiKey"
            ).body()

            Result.success(response.articles.map { it.toArticle() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}