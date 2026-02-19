package com.rapi.newsfeed.model

enum class Category { TECH, SPORT, BUSINESS, ENTERTAINMENT }

data class NewsItem(
    val id: Int,
    val title: String,
    val category: Category,
    val timestampMillis: Long
)

data class NewsDetail(
    val id: Int,
    val content: String,
    val author: String
)