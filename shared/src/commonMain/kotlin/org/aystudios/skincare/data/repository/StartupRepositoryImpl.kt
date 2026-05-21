package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.StartupApi
import org.aystudios.skincare.domain.StartupRepository
import org.aystudios.skincare.utils.AppLogger

class StartupRepositoryImpl(val api: StartupApi): StartupRepository {
    override suspend fun test(): ApiResult<String> {
        return safeApiCall {
            AppLogger.startup.d { "Checking API Status" }
            val response = api.startup()
            AppLogger.startup.d { "Successfully Started API $response" }
            response
        }
    }
}