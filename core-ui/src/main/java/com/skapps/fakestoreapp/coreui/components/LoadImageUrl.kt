package com.skapps.fakestoreapp.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import coil.compose.AsyncImage

@Composable
fun LoadImageFromUrl(
    imageUrl: String,
    contentDescription: String?=null,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Gray)
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            placeholder = placeholder,
            error = error
        )
    }
}