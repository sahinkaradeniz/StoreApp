package com.skapps.fakestoreapp.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skapps.fakestoreapp.coreui.theme.Purple80

/**
 * A reusable button composable that combines an icon and text for common actions.
 *
 * @param label The text displayed on the button.
 * @param icon The icon displayed next to the text.
 * @param onClick The action to perform when the button is clicked.
 * @param modifier Modifier for customizing the button's layout and appearance.
 * @param backgroundColor The background color of the button.
 * @param contentColor The color of the text and icon inside the button.
 * @param cornerRadius The corner radius for the button.
 * @param spacing The space between the icon and text.
 */
@Composable
fun IconTextButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Purple80,
    contentColor: Color = Color.Black,
    cornerRadius: Dp = 12.dp,
    spacing: Dp = 8.dp,
    textSize: TextUnit = 14.sp,
    iconSize: Dp = 20.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(cornerRadius),
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$label Icon",
                modifier = Modifier
                    .size(iconSize)
            )
            Spacer(modifier = Modifier.width(spacing))
            Text(
                text = label,
                color = contentColor,
                fontSize = textSize
            )
        }
    }
}