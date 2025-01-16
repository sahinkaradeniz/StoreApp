package com.skapps.basket

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skapps.fakestoreapp.core.loading.LoadingType
import com.skapps.fakestoreapp.coreui.R
import com.skapps.fakestoreapp.coreui.components.AppTopBar
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import com.skapps.fakestoreapp.coreui.theme.Purple80

@Composable
fun BasketScreen(viewModel: BasketViewModel = hiltViewModel(),onNavigateToCheckout: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activeLoadings by viewModel.activeLoadings.collectAsState()
    CollectSideEffect(sideEffect = viewModel.sideEffect) {
        when (it) {
            is BasketSideEffect.ShowError -> Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                .show()

            BasketSideEffect.NavigateCheckoutScreen -> {
                onNavigateToCheckout()
            }
        }
    }
    LaunchedEffect(Unit) { viewModel.onAction(BasketUiAction.LoadBasket) }
    Scaffold(
        topBar = { AppTopBar(title = "BASKET") },
        bottomBar = {
            BasketSummary(
                price = uiState.totalPrice,
                discount = uiState.totalDiscount,
                total = uiState.total
            ) { viewModel.onAction(BasketUiAction.Checkout) }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
        ) {
            if (uiState.isEmpty) {
                Text(
                    "No products in basket",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.items) { item ->
                        BasketItemRow(
                            item = item,
                            isLoading = activeLoadings.contains(LoadingType.Local(BasketViewModel.quantityLoadingKey+item.id)),
                            onIncrease = {
                                viewModel.onAction(
                                    BasketUiAction.IncreaseQuantity(
                                        item.id,
                                    )
                                )
                            },
                            onDecrease = {
                                viewModel.onAction(
                                    BasketUiAction.DecreaseQuantity(
                                        item.id,
                                    )
                                )
                            },
                            onRemove = {
                                viewModel.onAction(
                                    BasketUiAction.RemoveItem(
                                        item.id, "Product removing..."
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BasketItemRow(
    item: BasketItemUiModel,
    isLoading: Boolean,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.6f))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImageFromUrl(
                imageUrl = item.image,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(item.title, fontSize = 14.sp, color = Color.Black)
                Row {
                    Text("${item.price} TL", fontSize = 14.sp, color = Color.Black)
                    Spacer(Modifier.width(4.dp))
                    if (item.oldPrice > item.price) {
                        Text(
                            "${item.oldPrice} TL",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, enabled = !isLoading) {
                    Icon(
                        painterResource(id = R.drawable.minus_24),
                        contentDescription = null,
                        tint = if (!isLoading) Color.Black else Color.LightGray
                    )
                }
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
                } else {
                    Text(
                        "${item.quantity}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                IconButton(onClick = onIncrease, enabled = !isLoading) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = if (!isLoading) Color.Black else Color.LightGray
                    )
                }
                IconButton(onClick = onRemove, enabled = !isLoading) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun BasketSummary(price: Double, discount: Double, total: Double, onCheckoutClicked: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Price:")
                Text("Discount:")
                Text("Total:")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${price.toInt()} TL")
                Text("${discount.toInt()} TL")
                Text("${total.toInt()} TL")
            }
        }
        Button(
            onClick = onCheckoutClicked,
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple80),
        ) {
            Text("CHECKOUT", fontSize = 16.sp, color = Color.Black)
        }
    }
}
