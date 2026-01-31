package org.aystudios.skincare.utils

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.CategoryDTO

/**
 * Generic paginator
 * Key      -> page number (Int)
 * Response -> API response (CategoryDTO)
 * Item     -> single item (Product)
 */

class Paginator<Key, Response, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (Key) -> ApiResult<Response>,
    private val getNextKey: (Response, Key) -> Key,
    private val getItems: (Response) -> List<Item>,
    private val isLastPage: (Response) -> Boolean,
    private val onError: (String) -> Unit,
    private val onSuccess: (List<Item>, Boolean) -> Unit
)
{

    private var currentKey: Key = initialKey
    private var isMakingRequest = false

    suspend fun loadNext() {
        if (isMakingRequest) return

        isMakingRequest = true
        onLoadUpdated(true)

        when (val result = onRequest(currentKey)) {
            is ApiResult.Success -> {
                val response = result.data
                currentKey = getNextKey(response, currentKey)

                onSuccess(
                    getItems(response),
                    isLastPage(response)
                )
            }

            is ApiResult.Error -> onError(result.message)
            else -> Unit
        }

        onLoadUpdated(false)
        isMakingRequest = false
    }


    fun reset() {
        currentKey = initialKey
    }
}