package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.Content
import org.aystudios.skincare.domain.ProductRepository
import org.aystudios.skincare.domain.model.CategoryPagingState

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    // --- All Categories ---
    private val _allCategoryState = MutableStateFlow<ApiResult<List<String>>>(ApiResult.Idle)
    val allCategoryState = _allCategoryState.asStateFlow()

    init {
        getAllCategories()
    }

    fun getAllCategories(){
        viewModelScope.launch {
            _allCategoryState.value = ApiResult.Loading
            _allCategoryState.value = repository.getAllCategories()
        }
    }



    //Page
    private val pages = mutableMapOf<String, Int>()
    // --- CATEGORY STATES ---
    private val states = mutableMapOf<String, MutableStateFlow<CategoryPagingState<Content>>>()

    fun state(category: String): MutableStateFlow<CategoryPagingState<Content>> {
        return states.getOrPut(category) {
            MutableStateFlow(CategoryPagingState())
        }
    }

    fun load(category: String){
        val stateFlow = state(category)
        val s = stateFlow.value

        if (s.isLoading || s.endReached) return

        val page = pages.getOrPut(category) { 0 }

        viewModelScope.launch {
            stateFlow.value = s.copy(isLoading = true, error = null)

            when(val result = repository.getProductByCategory(category, page)){
                is ApiResult.Success -> {
                    val response = result.data
                    val current = stateFlow.value

                    stateFlow.value = current.copy(
                        items = current.items + response.content,
                        isLoading = false,
                        endReached = response.last
                    )

                    pages[category] = page + 1
                }

                is ApiResult.Error -> {
                    stateFlow.value = s.copy(error = result.message, isLoading = false)
                }
                else -> Unit
            }

        }
    }

    fun reset(category: String){
        pages[category] = 0
        states[category]?.value = CategoryPagingState()

    }


}
