package com.skapps.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skapps.fakestoreapp.coreui.components.IconTextButton
import com.skapps.fakestoreapp.coreui.components.LoadImageFromUrl
import com.skapps.fakestoreapp.coreui.components.SearchView
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SortType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectSideEffect(
        sideEffect = viewModel.sideEffect,
        onSideEffect = { effect ->
            when (effect) {
                is HomeSideEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }

                is HomeSideEffect.NavigateToProductDetail -> {
                    onNavigateToDetail(effect.id)
                }
            }
        }
    )

    if (uiState.isSortSheetVisible) {
        SortBottomSheet(
            onDismiss = {
                viewModel.onSortClicked()
            },
            onOptionSelected = { selectedOption ->
                viewModel.onSortOptionSelected(selectedOption)
            }
        )
    }

    Column {
        SearchFilterSortBar(
            query = uiState.query,
            onQueryChange = { query ->
                viewModel.onSearchQueryChanged(query)
            },
            clearQuery = {
                viewModel.onClearSearch()
            },
            onSortClicked = {
                viewModel.onSortClicked()
            },
            isSearchMode = uiState.isSearchMode
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductList(
            lazyPagingItems = if (uiState.isSearchMode) uiState.searchResults.collectAsLazyPagingItems() else uiState.products.collectAsLazyPagingItems(),
            onItemClicked = { productId ->
                viewModel.onProductClicked(productId.toString())
            }
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchFilterSortBar(
        query = "Search Query",
        onQueryChange = {},
        clearQuery = {},
        onSortClicked = {},
        isSearchMode = false
    )
}

@Composable
fun SearchFilterSortBar(
    query: String,
    onQueryChange: (String) -> Unit,
    clearQuery: () -> Unit,
    onSortClicked: () -> Unit,
    isSearchMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchView(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
                .align(Alignment.CenterVertically),
            query = query,
            onQueryChange = { query ->
                onQueryChange(query)
            },
            clearQuery = {
                clearQuery()
            },
            searchHint = stringResource(R.string.search),
            clearContentDescription = stringResource(R.string.clear)
        )
        if (!isSearchMode) {
            IconTextButton(
                label = stringResource(R.string.sort),
                icon = Icons.Default.KeyboardArrowDown,
                onClick = { onSortClicked() },
                spacing = 4.dp,
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .padding(end = 8.dp, top = 4.dp)
                    .align(Alignment.CenterVertically),
                textSize = 12.sp
            )
        }
    }
}

@Composable
fun ProductList(lazyPagingItems: LazyPagingItems<ProductEntity>, onItemClicked: (Int?) -> Unit) {
    LazyColumn {
        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item { BottomLoadingItem() }
            }

            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                val error = lazyPagingItems.loadState.refresh as LoadState.Error
                item {
                    ErrorItem(
                        message = error.error.localizedMessage ?: "Unknown Error",
                        onRetry = { lazyPagingItems.retry() }
                    )
                }
            }

            else -> {
                items(lazyPagingItems.itemCount) { index ->
                    lazyPagingItems[index]?.let { item ->
                        ProductItem(
                            productEntity = item,
                            onItemClicked = { productId ->
                                onItemClicked(productId)
                            }
                        )
                    }
                }
                lazyPagingItems.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> {
                            item { BottomLoadingItem() }
                        }

                        is LoadState.Error -> {
                            val appendError = loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = appendError.error.localizedMessage ?: "Unknown Error",
                                    onRetry = { retry() }
                                )
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}



@Composable
fun BottomLoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LazyColumnPreview() {
    LazyColumn {
        items(2) {
            ProductItem(
                productEntity = ProductEntity(
                    id = 1,
                    title = "Product Title",
                    description = "Product Description Product Description Product Description Product Description",
                    oldPrice = 100.0,
                    newPrice = 50.0,
                    category = "Category",
                    brand = "Brand",
                    rating = 4.5,
                    stock = 10,
                    discountPercentage = 50.0,
                    thumbnail = "https://picsum.photos/200/300",
                    basketQuantity = 0,
                    isFavorite = false,
                    images = listOf("https://picsum.photos/200/300")
                ),
                onItemClicked = {}
            )
        }

        item {
            BottomLoadingItem()
        }
        item {
            ErrorItem(message = "Failed to load more items", onRetry = {})
        }
    }
}

@Composable
fun ProductItem(
    productEntity: ProductEntity,
    onItemClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(8.dp)
            )
            .padding(end = 16.dp, start = 8.dp)
            .clickable { onItemClicked(productEntity.id) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LoadImageFromUrl(
            imageUrl = productEntity.thumbnail,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp, top = 12.dp)
                .size(52.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = productEntity.title,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = productEntity.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        PriceRow(
            oldPrice = productEntity.oldPrice.toString(),
            price = productEntity.newPrice.toString(),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun PriceRow(
    oldPrice: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {
        Text(text = price, fontSize = 16.sp, color = com.skapps.fakestoreapp.coreui.theme.Purple40)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = oldPrice, color = Color.Gray)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    onDismiss: () -> Unit,
    onOptionSelected: (SortType) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.sorting_options), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            SortType.values().forEach { option ->
                Text(
                    text = option.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected(option)
                        }
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}