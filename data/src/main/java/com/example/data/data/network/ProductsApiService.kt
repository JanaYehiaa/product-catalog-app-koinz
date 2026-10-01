package com.example.data.data.network

import com.example.data.data.dto.ProductDto
import com.example.data.data.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiService {

    @GET("products")
    suspend fun getAllProducts() : ProductResponseDto

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") productId: Int): ProductDto

    @GET("products/search")
    suspend fun searchProducts(@Query("q") q: String): ProductResponseDto
}