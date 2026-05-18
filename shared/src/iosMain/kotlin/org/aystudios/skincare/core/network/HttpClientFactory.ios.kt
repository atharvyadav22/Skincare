package org.aystudios.skincare.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun providesHttpEngine(): HttpClientEngine = Darwin.create()