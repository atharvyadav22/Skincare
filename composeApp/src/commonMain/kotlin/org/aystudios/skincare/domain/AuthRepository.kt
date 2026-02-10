package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult

interface AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<Unit>
}
