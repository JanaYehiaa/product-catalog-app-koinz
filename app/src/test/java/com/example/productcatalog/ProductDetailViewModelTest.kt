package com.example.productcatalog

import com.example.data.data.model.Product
import com.example.data.data.repository.ProductRepository
import com.example.productcatalog.core.common.getErrorMessage
import com.example.productcatalog.features.productdetails.presentation.ProductDetailUIState
import com.example.productcatalog.features.productdetails.presentation.ProductDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Rule
import org.junit.Test

class ProductDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val mockRepository = mockk<ProductRepository>()
    private val product = mockk<Product>(relaxed = true)
    private val productId = 7
    private fun createViewModel() = ProductDetailViewModel(mockRepository, productId)

    @Test
    fun productDetailViewModel_GetProductDetail_Success() = runTest {
        coEvery { mockRepository.getProduct(productId) } returns product
        coEvery { mockRepository.getProduct(productId) } returns product
        val viewModel = createViewModel()

        assertEquals(ProductDetailUIState.Success(product), viewModel.uiState.value)
    }

    @Test
    fun productDetailViewModel_GetProductDetail_Error() = runTest {
        val exception = IOException("No Internet")
        coEvery { mockRepository.getProduct(productId) } throws exception
        val viewModel = createViewModel()

        assertEquals(
            ProductDetailUIState.Error(getErrorMessage(exception)),
            viewModel.uiState.value
        )
    }

    @Test
    fun productDetailViewModel_GetProductDetail_LoadingThenSuccess() = runTest {
        coEvery { mockRepository.getProduct(productId) } coAnswers {
            delay(1_000)
            product
        }
        val viewModel = createViewModel()
        assertEquals(ProductDetailUIState.Loading, viewModel.uiState.value)

        advanceUntilIdle()
        assertEquals(ProductDetailUIState.Success(product), viewModel.uiState.value)
    }
}