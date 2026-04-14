package com.rapi.newsreader

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform