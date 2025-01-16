package com.skapps.fakestoreapp.coreui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    clearQuery: () -> Unit,
    searchHint: String,
    clearContentDescription: String
) {
    OutlinedTextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        label = { Text(text = searchHint) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = searchHint
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = clearContentDescription,
                    modifier = Modifier.clickable {
                        clearQuery()
                    }
                )
            }
        }
    )
}
