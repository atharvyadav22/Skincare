package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.domain.FavouriteRepository

class FavouritesViewModel(
    private val repository: FavouriteRepository
) : ViewModel() {

    private val _favourites =
        MutableStateFlow(ListPagingUIState<ProductItemDTO>())

    val favourites = _favourites.asStateFlow()

    init {
        loadFavourites()
    }

    fun loadFavourites() {
        viewModelScope.launch {

            _favourites.update {
                it.copy(isLoading = true, error = null)
            }

            when (val response = repository.getAllFavourites()) {

                is ApiResult.Success -> {
                    _favourites.update {
                        it.copy(
                            isLoading = false,
                            items = response.data
                        )
                    }
                }

                is ApiResult.Error -> {
                    _favourites.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    fun toggleFavourite(product: ProductItemDTO) {
        viewModelScope.launch {

            when (val response = repository.toggleFavourite(product.id)) {

                is ApiResult.Success -> {

                    val isFav = response.data.isFavourite

                    _favourites.update { current ->

                        val updatedList = if (isFav) {
                            current.items + product
                        } else {
                            current.items.filterNot { it.id == product.id }
                        }

                        current.copy(items = updatedList)
                    }
                }

                else -> Unit
            }
        }
    }

    fun isFavourite(productId: Long): Boolean {
        return _favourites.value.items.any { it.id == productId }
    }
}