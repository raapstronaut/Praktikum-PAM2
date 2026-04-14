package com.rapi.newsreader.model

data class Article(
    val title: String,
    val description: String,
    val imageUrl: String,
    val content: String,
    val sourceName: String,
    val publishedAt: String,
    val url: String
)