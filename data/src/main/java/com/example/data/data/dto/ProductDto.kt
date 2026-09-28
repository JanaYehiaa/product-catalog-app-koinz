package com.example.data.data.dto

import kotlinx.serialization.Serializable

data class ProductDto(
    val id: Int,
    val price: Double,
    val rating: Double,
    val title: String,
    val category: String,
    val thumbnail: String,
    val description: String,
    val reviews: List<ReviewDto>,
    val images: List<String>
)
