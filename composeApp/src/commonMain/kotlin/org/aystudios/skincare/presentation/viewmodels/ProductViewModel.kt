package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.domain.ProductRepository

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    // --- All Categories ---
    private val _allCategoryState = MutableStateFlow(ListPagingUIState<String>(isLoading = true))
    val allCategoryState = _allCategoryState.asStateFlow()

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            when (val response = repository.getAllCategories()) {
                is ApiResult.Success -> {
                    _allCategoryState.value = ListPagingUIState(items = response.data)
                }

                is ApiResult.Error -> {
                    _allCategoryState.value = ListPagingUIState(error = response.message)
                }

                else -> Unit
            }
        }
    }


    //Page
    private val pages = mutableMapOf<String, Int>()

    // --- CATEGORY STATES ---
    private val states = mutableMapOf<String, MutableStateFlow<ListPagingUIState<ProductItemDTO>>>()

    fun state(category: String): MutableStateFlow<ListPagingUIState<ProductItemDTO>> {
        return states.getOrPut(category) {
            MutableStateFlow(ListPagingUIState())
        }
    }

    fun load(category: String) {
        val stateFlow = state(category)
        val currentState = stateFlow.value

        if (currentState.isLoading || currentState.endReached) return

        val page = pages[category] ?: 0

        viewModelScope.launch {
            stateFlow.value = currentState.copy(isLoading = true, error = null)

            when (val result = repository.getProductByCategory(category, page)) {

                is ApiResult.Success -> {
                    val response = result.data
                    val updatedState = stateFlow.value

                    stateFlow.value = updatedState.copy(
                        items = updatedState.items + response.content,
                        isLoading = false,
                        endReached = response.last
                    )

                    pages[category] = page + 1
                }

                is ApiResult.Error -> {
                    stateFlow.value =
                        stateFlow.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                }

                else -> Unit
            }
        }
    }

    fun reset(category: String) {
        pages[category] = 0
        states[category]?.value = ListPagingUIState()

    }


    // ----- Get Product By Id -----
    data class GetProductByIdUiState(
        val response: ProductItemDTO? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _getProductById = MutableStateFlow(GetProductByIdUiState())
    val getProductById = _getProductById.asStateFlow()

    fun getProductById(id: Long) {
        viewModelScope.launch {
            _getProductById.value = GetProductByIdUiState(isLoading = true)

            when (val result = repository.getProductById(id)) {
                is ApiResult.Success -> {
                    _getProductById.value = GetProductByIdUiState(response = result.data)
                }

                is ApiResult.Error -> {
                    _getProductById.value = GetProductByIdUiState(error = result.message)
                }

                else -> Unit
            }
        }
    }


    // ----- Search Products -----
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // We transform the query flow into a state flow directly
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchState: StateFlow<ListPagingUIState<ProductItemDTO>> = _searchQuery
        .debounce(400)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                // Return an empty state immediately if the query is empty
                flowOf(ListPagingUIState())
            } else {
                // Convert your repository call into a flow
                flow {
                    emit(ListPagingUIState(isLoading = true))

                    val newState = when (val result = repository.getSearchProducts(query, 1)) {
                        is ApiResult.Success -> ListPagingUIState(items = result.data.content)
                        is ApiResult.Error -> ListPagingUIState(error = result.message)
                        else -> ListPagingUIState()
                    }
                    emit(newState)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Keeps flow alive for 5s after UI stops listening
            initialValue = ListPagingUIState()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
