package org.aystudios.skincare.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun providesHttpEngine() : HttpClientEngine = OkHttp.create()