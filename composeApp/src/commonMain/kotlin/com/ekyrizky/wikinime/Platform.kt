package com.ekyrizky.wikinime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform