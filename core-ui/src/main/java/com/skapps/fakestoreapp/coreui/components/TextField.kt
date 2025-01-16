package com.skapps.fakestoreapp.coreui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String = "",
    isError: Boolean = false,
    isValid: Boolean = false,
    leadIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    val defaultBorderColor = Color(0xFFC2C2C2)
    val errorColor = Color(0xFFB00020)
    val validColor = Color(0xFF1E8E3E)
    val contentColor = Color.Black

    val borderColor = when {
        isError -> errorColor
        isValid -> validColor
        else -> defaultBorderColor
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = contentColor
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadIcon != null) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    leadIcon()
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolder,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = defaultBorderColor.copy(alpha = 0.7f)
                    )
                }
                innerTextField()
            }
            if (trailingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    trailingIcon()
                }
            }
        }
    }
}