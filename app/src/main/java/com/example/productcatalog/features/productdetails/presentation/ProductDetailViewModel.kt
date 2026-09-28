package com.example.productcatalog.features.productdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.repository.ProductRepository
import com.example.productcatalog.core.common.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductDetailViewModel(
    val repository: ProductRepository,
    private val productId:Int
): ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductDetailUIState>(
            ProductDetailUIState.Loading
        )

    val uiState: StateFlow<ProductDetailUIState> =
        _uiState

    init {
        getSingleProduct(productId)
    }

    fun getSingleProduct(id: Int) {
        viewModelScope.launch {
            _uiState.value = try {
                val result = repository.getProduct(id)
                ProductDetailUIState.Success(result)
            } catch (e: Exception) {
                ProductDetailUIState.Error(getErrorMessage(e))
            }
        }
    }

}