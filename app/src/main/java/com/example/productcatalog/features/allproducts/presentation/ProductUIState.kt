package com.example.productcatalog.features.allproducts.presentation

import com.example.data.data.model.Product

sealed interface ProductUIState {
    data object Loading : ProductUIState
    data class Empty(val message: String) : ProductUIState
    data class Error(val message: String) : ProductUIState
    data class Success(
        val products: List<Product>, val isRefreshing: Boolean = false
    ) : ProductUIState

}