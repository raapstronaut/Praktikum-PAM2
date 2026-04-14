package com.rapi.newsreader.model

fun ArticleDto.toArticle(): Article {
    return Article(
        title = title ?: "No title",
        description = description ?: "No description",
        imageUrl = urlToImage ?: "",
        content = content ?: description ?: "No content",
        sourceName = source?.name ?: "Unknown source",
        publishedAt = publishedAt ?: "-",
        url = url ?: ""
    )
}