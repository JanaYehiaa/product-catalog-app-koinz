package com.example.productcatalog

import com.example.data.data.model.Product
import com.example.data.data.repository.ProductRepository
import com.example.productcatalog.core.common.getErrorMessage
import com.example.productcatalog.features.allproducts.presentation.ProductUIState
import com.example.productcatalog.features.allproducts.presentation.ProductViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ProductViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val mockRepository = mockk<ProductRepository>()
    private val products = listOf(mockk<Product>(relaxed = true), mockk<Product>(relaxed = true))

    private fun createViewModel() = ProductViewModel(mockRepository)


    @Test
    fun productViewModel_GetAllProducts_Success() {
        coEvery { mockRepository.getAllProducts() } returns products
        val viewModel = createViewModel()

        assertEquals(ProductUIState.Success(products), viewModel.uiState.value)
    }

    @Test
    fun productViewModel_GetAllProducts_ErrorNoInternet() = runTest {
        val exception = IOException("No Internet")
        coEvery { mockRepository.getAllProducts() } throws exception
        val viewModel = createViewModel()

        assertEquals(ProductUIState.Error(getErrorMessage(exception)), viewModel.uiState.value)
    }

    @Test
    fun productViewModel_GetAllProducts_ErrorRepository() = runTest {
        val exception = RuntimeException("Server error")
        coEvery { mockRepository.getAllProducts() } throws exception

        val viewModel = createViewModel()

        assertEquals(ProductUIState.Error(getErrorMessage(exception)), viewModel.uiState.value)
    }

    @Test
    fun productViewModel_GetAllProducts_Empty() = runTest {
        coEvery { mockRepository.getAllProducts() } returns emptyList()
        val viewModel = createViewModel()

        assertEquals(ProductUIState.Empty("No products found"), viewModel.uiState.value)

    }
    @Test
    fun productViewModel_GGetAllProducts_LoadingThenSuccess() = runTest {
        coEvery { mockRepository.getAllProducts() } coAnswers {
            delay(1_000)
            products
        }
        val viewModel = createViewModel()
        TestCase.assertEquals(ProductUIState.Loading, viewModel.uiState.value)

        advanceUntilIdle()
        TestCase.assertEquals(ProductUIState.Success(products), viewModel.uiState.value)
    }

    @Test
    fun productDetailViewModel_GetAllProducts_RefreshThenShowData() = runTest {
        coEvery { mockRepository.getAllProducts() } returns products
        val viewModel = createViewModel()
        val newProducts = listOf(mockk<Product>(relaxed = true), mockk<Product>(relaxed = true))

        coEvery { mockRepository.getAllProducts() } coAnswers {
            delay(1_000)
            newProducts
        }
        viewModel.getProductList(isRefresh = true)
        assertEquals(ProductUIState.Success(products, isRefreshing = true), viewModel.uiState.value)

        advanceUntilIdle()

        assertEquals(ProductUIState.Success(newProducts, isRefreshing = false), viewModel.uiState.value)

    }

    @Test
    fun productViewModel_GetAllProducts_SuccessAfterError() = runTest {
        coEvery { mockRepository.getAllProducts() } throws IOException("No Internet")
        val viewModel = createViewModel()

        coEvery { mockRepository.getAllProducts() } returns products
        viewModel.getProductList()

        TestCase.assertEquals(ProductUIState.Success(products), viewModel.uiState.value)
    }
}