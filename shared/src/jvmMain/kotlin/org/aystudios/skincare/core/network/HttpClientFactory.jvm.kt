package org.aystudios.skincare.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

actual fun providesHttpEngine(): HttpClientEngine = CIO.create()