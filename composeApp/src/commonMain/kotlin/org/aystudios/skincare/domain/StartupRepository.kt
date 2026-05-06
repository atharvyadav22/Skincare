package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult

interface StartupRepository {

    suspend fun test(): ApiResult<String>
}