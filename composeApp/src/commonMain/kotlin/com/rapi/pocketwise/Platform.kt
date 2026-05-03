package com.rapi.pocketwise

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform