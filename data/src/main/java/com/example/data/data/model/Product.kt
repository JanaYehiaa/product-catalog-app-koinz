package com.example.data.data.model

import kotlinx.serialization.Serializable

data class Product(
    val id: Int,
    val price: Double,
    val rating: Double,
    val reviewNumber: Int,
    val title: String,
    val category: String,
    val thumbnail: String,
    val description: String,
    val images: List<String>,
)
