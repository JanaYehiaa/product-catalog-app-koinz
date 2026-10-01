package com.example.data.data.di

import com.example.data.data.network.ProductsApiService
import com.example.data.data.repository.ProductRepository
import com.example.data.data.repository.ProductRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://dummyjson.com/"

val dataModule = module{

    single {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ProductsApiService> {
        get<Retrofit>().create(ProductsApiService::class.java)
    }

    single<ProductRepository>{
        ProductRepositoryImpl(get())
    }


}
