package com.example.productcatalog.features.productdetails.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.productcatalog.features.allproducts.ui.theme.ProductCatalogTheme

class ProductDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        setContent {
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {finish()}
                )
            }
        }
    }