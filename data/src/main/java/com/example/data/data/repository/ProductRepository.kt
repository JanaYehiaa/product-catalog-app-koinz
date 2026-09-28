package com.example.data.data.repository

import com.example.data.data.model.Product


interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProduct(id: Int): Product
    suspend fun searchProducts(q: String): List<Product>
}