package com.example.productcatalog

import android.app.Application
import com.example.data.data.di.dataModule
import com.example.productcatalog.features.allproducts.di.productListModule
import com.example.productcatalog.features.productdetails.di.productDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(productListModule, productDetailModule, dataModule)
        }
    }
}