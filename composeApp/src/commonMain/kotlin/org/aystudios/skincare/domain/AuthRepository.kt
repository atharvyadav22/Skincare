package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.CategoryDTO
import org.aystudios.skincare.data.remote.dto.LoginResponseDTO

interface AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<Unit>
}
