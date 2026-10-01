package com.example.productcatalog.features.allproducts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.repository.ProductRepository
import com.example.productcatalog.core.common.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductUIState>(
            ProductUIState.Loading
        )

    val uiState: StateFlow<ProductUIState> =
        _uiState

    init {
        getProductList()
    }

    fun getProductList(isRefresh: Boolean = false) {
        viewModelScope.launch {

            if (isRefresh) {
                val currentState = _uiState.value
                if (currentState is ProductUIState.Success) {
                    _uiState.value = currentState.copy(
                        isRefreshing = true
                    )
                }
            }

            _uiState.value = try {
                val products = repository.getAllProducts()
                if (products.isEmpty()) {
                    ProductUIState.Empty("No products found")
                } else {
                    ProductUIState.Success(products)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ProductUIState.Error(getErrorMessage(e))
            }
        }
    }


}