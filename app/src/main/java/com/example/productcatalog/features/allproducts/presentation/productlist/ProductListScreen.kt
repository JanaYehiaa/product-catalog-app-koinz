package com.example.productcatalog.features.allproducts.presentation.productlist

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.data.model.Product
import com.example.productcatalog.R
import com.example.productcatalog.features.allproducts.presentation.ProductUIState
import com.example.productcatalog.features.allproducts.presentation.ProductViewModel
import com.example.productcatalog.features.productdetails.presentation.ProductDetailActivity
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = koinViewModel(),
    modifier: Modifier = Modifier

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        ProductUIState.Loading -> {
            CircularProgressIndicator()
        }

        is ProductUIState.Error -> {
            Text(text = state.message)
        }

        is ProductUIState.Empty -> {
            Text(text = state.message)

        }

        is ProductUIState.Success -> {
            ProductListContent(
                products = state.products,
                isRefreshing = state.isRefreshing,
                modifier = modifier,
                onRefresh = { viewModel.getProductList(isRefresh = true) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListContent(
    products: List<Product>,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    val context = LocalContext.current
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = Color(0xFF9CA3AF),
                color = Color(0xFF1A1A1A),
                state = state
            )
        },
    ) {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(modifier = modifier.padding(top = 48.dp, bottom = 12.dp)) {
                    Text(
                        text = "New Arrivals", style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            letterSpacing = (-0.75).sp,
                            color = Color(0xFF1A1A1A)

                        )
                    )
                    Text(
                        text = "Discover our latest collection",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            letterSpacing = 0.sp,
                            color = Color(0xFF6B7280)
                        ),
                    )
                }
            }

            item {
                SearchBar(modifier)

            }

            items(products) { product ->
                ProductItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 9.dp, bottom = 15.dp, end = 9.dp),
                    product = product,
                    onClick = {
                        val intent = Intent(
                            context,
                            ProductDetailActivity::class.java
                        ).apply {
                            putExtra("PRODUCT_ID", product.id)
                        }

                        context.startActivity(intent)
                    }
                )
            }

        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F7))

            .padding(horizontal = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Search products...",
            color = Color(0xFF9CA3AF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}