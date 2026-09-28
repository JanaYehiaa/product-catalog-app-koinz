package com.example.productcatalog.features.allproducts.di

import com.example.productcatalog.features.allproducts.presentation.ProductViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productListModule = module{
    viewModel {
        ProductViewModel(get())
    }
}