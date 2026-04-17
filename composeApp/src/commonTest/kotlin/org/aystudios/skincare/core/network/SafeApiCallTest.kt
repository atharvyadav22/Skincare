package org.aystudios.skincare.core.network

import kotlinx.coroutines.test.runTest
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO
import org.junit.Test

//class SafeApiCallTest {
//
//    @Test
//    fun safeApiCall_whenSuccess_returnsSuccess() = runTest {
//        val mockResponse = UserProfileResponseDTO("dev.atharvyadav@gmail.com", "Atharv Yadav", "123456789", "Delusional World")
//        val result = safeApiCall { mockResponse }
//
//        assertTrue(result is ApiResult.Success)
//        val successResult = result.data
//        assertEquals(mockResponse, successResult)
//    }
//
//    @Test
//    fun safeApiCall_whenGenericExceptionThrows_returnsError() = runTest {
//        val result = safeApiCall { throw Exception("Network Failure") }
//
//        assertTrue(result is ApiResult.Error)
//
//        val error = result.message
//        assertEquals("Network Failure", error)
//
//    }
//
//}