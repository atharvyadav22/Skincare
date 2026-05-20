package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageDTO(
    val content: List<ProductItemDTO>,
    val last: Boolean,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)