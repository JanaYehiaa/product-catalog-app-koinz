package com.example.data.data.repository

import com.example.data.data.mapper.toProduct
import com.example.data.data.model.Product
import com.example.data.data.network.ProductsApiService

class ProductRepositoryImpl(
    private val api: ProductsApiService
): ProductRepository{

    override suspend fun getAllProducts(): List<Product>{
        val response = api.getAllProducts()
        return response.products.map { it.toProduct() }
    }

    override suspend fun getProduct(id: Int): Product {
        val response = api.getProduct(id)
        return response.toProduct()
    }

    override suspend fun searchProducts(q: String): List<Product> {
        val response = api.searchProducts(q)
        return response.products.map { it.toProduct() }
    }

}