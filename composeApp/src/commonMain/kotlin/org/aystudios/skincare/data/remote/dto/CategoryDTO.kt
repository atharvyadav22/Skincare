package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    val content: List<Content>,
    val last: Boolean,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)