package com.github.oxarnau.transsectes_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform