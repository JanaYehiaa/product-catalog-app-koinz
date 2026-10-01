package com.example.productcatalog.features.productdetails.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.data.model.Product
import com.example.productcatalog.R
import com.example.productcatalog.core.components.LoadErrorPage
import com.example.productcatalog.core.components.LoadingPage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {

    val viewModel = koinViewModel<ProductDetailViewModel>(
        parameters = { parametersOf(productId) }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        ProductDetailUIState.Loading -> {
            LoadingPage()
        }

        is ProductDetailUIState.Success -> {
            ProductDetailContent(
                product = state.product,
                modifier = modifier,
                onBackClick = onBackClick
            )
        }

        is ProductDetailUIState.Error -> {
            LoadErrorPage(state.message)
        }

//        is ProductDetailUIState.Empty -> {
//            LoadErrorPage(state.message)
//        }
    }
}

@Composable
fun ProductDetailContent(product: Product, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .padding(top = 38.dp, start = 23.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .clickable {
                        onBackClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back"
                )
            }



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "product thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.category.uppercase(),
                        style = TextStyle(
                            //fontFamily = Inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            letterSpacing = 2.4.sp,
                            color = Color(0xFF9CA3AF)
                        )
                    )

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "Star"
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = product.rating.toString(),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                letterSpacing = 1.66.sp,
                                color = Color(0xFF1A1A1A)
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "(${product.reviewNumber} reviews)",
                            style = TextStyle(
                                //fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                letterSpacing = (-0.2).sp,
                                color = Color(0xFF9CA3AF)
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.title,
                    style = TextStyle(
                        //fontFamily = Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 36.sp,
                        letterSpacing = 0.29.sp,
                        color = Color(0xFF1A1A1A)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${product.price}",
                    style = TextStyle(
                        //fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.sp,
                        color = Color(0xFF1A1A1A)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "DESCRIPTION",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 1.4.sp,
                        color = Color(0xFF1A1A1A)
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = product.description,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 26.sp,
                        color = Color(0xFF6B7280)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        item {
            SelectorsRow(modifier)
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Footer(modifier)

        }
    }
}


@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color(0xFFE5E7EB),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.cart),
                    contentDescription = "Cart"
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Add to cart",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color.White
                    )
                )
            }
        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(Color.White)
                .border(
                    width = 2.dp,
                    color = Color(0xFFF5F5F7),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Image(
                painter = painterResource(R.drawable.export),
                contentDescription = "Export",

            )
        }
    }
}

@Composable
fun ColorDot(color: Color, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .then(
                if (selected) Modifier.border(2.dp, Color(0xFF1A1A1A), CircleShape)
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Composable
fun SelectorsRow(modifier: Modifier = Modifier) {
    var selectedColorIndex by rememberSaveable { mutableIntStateOf(0) }
    var quantity by rememberSaveable { mutableIntStateOf(1) }

    val colors = listOf(Color(0xFF1A1A1A), Color(0xFFE5E7EB), Color(0xFFD4C4B0))

    Row(modifier = modifier.fillMaxWidth()
        .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "COLOR",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.2.sp,
                    color = Color(0xFF1A1A1A)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                colors.forEachIndexed { index, color ->
                    ColorDot(
                        color = color,
                        selected = index == selectedColorIndex,
                        onClick = { selectedColorIndex = index }
                    )
                }
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "QUANTITY",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.2.sp,
                    color = Color(0xFF1A1A1A)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F7)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "-",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { if (quantity > 0) quantity-- }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                )
                Text(
                    text = quantity.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "+",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { quantity++ }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}
