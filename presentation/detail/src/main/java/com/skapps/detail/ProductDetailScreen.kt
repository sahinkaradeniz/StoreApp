package com.skapps.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.theme.Purple40
import com.skapps.fakestoreapp.coreui.theme.Purple80
import com.skapps.fakestoreapp.coreui.theme.logError

@Composable
fun ProductDetailScreen(
    productId: String,
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = productId) {
        viewModel.onAction(ProductDetailUiAction.LoadProduct(productId))
    }

    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = uiState.product.title,
                isFavorite = uiState.isFavorite,
                backAction = {
                    	navController.popBackStack()
                },
                favoriteAction = {
                    viewModel.onAction(
                        ProductDetailUiAction.FavoriteClicked(
                            "The process is going on now..."
                        )
                    )
                }
            )
        },
        bottomBar = {
            AddToCartButton(onClick = {
                viewModel.onAction(
                    ProductDetailUiAction.AddToCartClicked(
                        "The product is being added to cart..."
                    )
                )
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            ProductImage(imageUrl = uiState.product.thumbnail)
            DetailContent(
                title = uiState.product.title,
                price = uiState.product.price,
                oldPrice = uiState.product.oldPrice,
                description = uiState.product.description
            )
        }
    }
}


@Composable
fun DetailContent(
    title: String,
    price: Double,
    oldPrice: Double,
    description: String,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = Color.Black,
                fontSize = 16.sp
            )
            Price(
                price = price,
                originalPrice = oldPrice,
                modifier = Modifier
            )
        }
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Text(
            text = description,
            modifier = Modifier.padding(10.dp),
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}


@Composable
fun Price(
    price: Double,
    originalPrice: Double,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = "$price",
            color = Color.Black,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$originalPrice",
            color = Color.Gray,
            fontSize = 16.sp,
            textDecoration = TextDecoration.LineThrough
        )
    }
}


@Composable
fun AddToCartButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple40,
            contentColor = Color.White
        )
    ) {
        Text(text = "Add to Cart", fontSize = 16.sp)
    }
}

@Composable
fun ProductImage(
    imageUrl: String
) {
    LoadImageFromUrl(
        imageUrl = imageUrl,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Purple40)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    isFavorite: Boolean,
    backAction: () -> Unit,
    favoriteAction: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Purple80.copy(alpha = 0.3f)
        ),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { backAction() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { favoriteAction() }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (isFavorite) Color.Red else Color.Black,
                    contentDescription = "Favorite Icon"
                )
            }
        }
    )
}
