package com.example.data.data.mapper

import com.example.data.data.dto.ProductDto
import com.example.data.data.model.Product

fun ProductDto.toProduct() : Product {
    return Product (
        id = id,
        title = title,
        price = price,
        category = category,
        thumbnail = thumbnail,
        rating = rating,
        reviewNumber = reviews.size,
        images = images,
        description = description
        )
}
