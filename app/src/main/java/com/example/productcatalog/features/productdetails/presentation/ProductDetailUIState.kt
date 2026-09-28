package com.example.productcatalog.features.productdetails.presentation

import com.example.data.data.model.Product
import com.example.productcatalog.features.allproducts.presentation.ProductUIState

interface ProductDetailUIState {
    data object Loading: ProductDetailUIState
    data class Empty(val message:String): ProductDetailUIState
    data class Error(val message:String): ProductDetailUIState
    data class Success(val product: Product): ProductDetailUIState

}