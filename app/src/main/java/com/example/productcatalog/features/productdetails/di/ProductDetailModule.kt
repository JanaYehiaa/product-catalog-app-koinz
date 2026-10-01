package com.example.productcatalog.features.productdetails.di

import com.example.productcatalog.features.productdetails.presentation.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productDetailModule = module{
    viewModel { (productId: Int) ->
        ProductDetailViewModel(
            repository = get(),
            productId = productId
        )
    }
}