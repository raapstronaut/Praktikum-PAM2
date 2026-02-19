package com.rapi.newsfeed.uii

import com.rapi.newsfeed.model.Category

data class NewsUiItem(
    val id: Int,
    val headline: String,
    val badge: String,
    val category: Category

)