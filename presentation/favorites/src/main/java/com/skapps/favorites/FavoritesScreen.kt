package com.skapps.favorites

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skapps.fakestoreapp.coreui.components.AppTopBar
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import com.skapps.fakestoreapp.coreui.theme.Purple40
import com.skapps.fakestoreapp.coreui.theme.poppinsFontFamily

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onNavigateToProductDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    CollectSideEffect(
        sideEffect = viewModel.sideEffect,
        onSideEffect = { effect ->
            when (effect) {
                is FavoritesSideEffect.NavigateToProductDetail -> {
                    onNavigateToProductDetail(effect.id)
                }
                is FavoritesSideEffect.ShowErrorGetFavorites -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
                is FavoritesSideEffect.ShowErrorDeleteFavorites -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
                is FavoritesSideEffect.ShowSuccessDeleteFavorites -> {
                    Toast.makeText(context, "Product removed from favorites", Toast.LENGTH_LONG).show()
                }
                is FavoritesSideEffect.ShowErrorAddToCart -> {
                    Toast.makeText(context, effect.error, Toast.LENGTH_LONG).show()
                }
                FavoritesSideEffect.ShowSuccessAddToCart -> {
                    Toast.makeText(context, "Product added to cart", Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = "FAVORITE"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.1f))
        ) {
            if (uiState.isEmpty) {
                Text(
                    text = "No favorite products",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                FavoriteGrid(
                    favorites = uiState.favorites,
                    onItemClicked = { productId ->
                        viewModel.onAction(FavoritesUiAction.ProductClicked(productId.toString()))
                    },
                    onFavoriteClicked = { id ->
                        viewModel.onAction(
                            FavoritesUiAction.FavoriteButtonClicked(
                                loadingMessage = "The process of changing favorites...",
                                id = id.toString()
                            )
                        )
                    },
                    addToCart = { item ->
                        viewModel.onAction(
                            FavoritesUiAction.AddToCartClicked(
                                loadingMessage = "The product is being added to cart...",
                                item = item
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun FavoriteGrid(
    favorites: List<FavoriteUiModel>,
    onItemClicked: (Int) -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    addToCart: (FavoriteUiModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(favorites) { item ->
            FavoriteItem(
                favoriteItem = item,
                onItemClicked = { onItemClicked(item.id) },
                onFavoriteClicked = { onFavoriteClicked(item.id) },
                addToCart = { addToCart(item) }
            )
        }
    }
}

@Composable
fun FavoriteItem(
    favoriteItem: FavoriteUiModel,
    onItemClicked: () -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    addToCart: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClicked() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Üst kısımda resmi ve favori (kalp) ikonunu üst- sağda göstermek için Box kullandık
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp) // Resmin yüksekliği
            ) {
                LoadImageFromUrl(
                    imageUrl = favoriteItem.thumbnail,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
                Icon(
                    imageVector = if (favoriteItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (favoriteItem.isFavorite) Color.Red else Color.Black,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable {
                            onFavoriteClicked(favoriteItem.id)
                        }
                )
            }

            // Ürün başlık ve açıklaması
            Text(
                text = favoriteItem.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = favoriteItem.description,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${favoriteItem.price} TL",
                    fontSize = 14.sp,
                    color = Purple40,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.Black,
                    contentDescription = "Add Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            addToCart(favoriteItem.id)
                        }
                )
            }
        }
    }
}