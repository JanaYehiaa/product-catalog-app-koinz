package com.example.productcatalog.features.allproducts.presentation.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.data.model.Product
import com.example.productcatalog.R


@Composable
fun ProductItem(modifier: Modifier = Modifier, product: Product, onClick: () -> Unit) {
    Column(modifier = modifier.clickable{onClick()}) {
        Box( modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(product.thumbnail)
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "product thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            FavoriteButton(modifier = Modifier.align(Alignment.TopEnd).padding(10.dp))
        }
        Text(
            text = product.category.uppercase(),
            style = TextStyle(
                //fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.2.sp,
                color = Color(0xFF9CA3AF)
            )
        )
        Text(
            text = product.title,
            style = TextStyle(
                //fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.29.sp,
                color = Color(0xFF1A1A1A)
            )
        )
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
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier
) {
    var isToggled by rememberSaveable { mutableStateOf(false) }

    IconButton(
        onClick = { isToggled = !isToggled },
        modifier = modifier.background(
            color = Color.White,
            shape = CircleShape
        )
    ) {
        Icon(
            painter = if (isToggled) {
                painterResource(R.drawable.favorite_filled)
            } else {
                painterResource(R.drawable.favorite)
            },
            contentDescription = if (isToggled) {
                "Remove from favorites"
            } else {
                "Add to favorites"
            }
        )
    }
}