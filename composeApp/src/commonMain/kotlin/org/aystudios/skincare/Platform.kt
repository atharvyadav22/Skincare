package org.aystudios.skincare

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform