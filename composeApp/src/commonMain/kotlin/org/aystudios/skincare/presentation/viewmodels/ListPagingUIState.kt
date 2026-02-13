package org.aystudios.skincare.presentation.viewmodels

data class ListPagingUIState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)