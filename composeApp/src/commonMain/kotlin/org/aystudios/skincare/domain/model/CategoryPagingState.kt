package org.aystudios.skincare.domain.model

data class CategoryPagingState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)
