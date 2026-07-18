package com.rm.postapp.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.rm.postapp.R

@Composable
fun IconTextButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    text: String? = null,
    tint: Color = LocalContentColor.current,
    iconDescription: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = tint
        )

        text?.let {
            Spacer(
                modifier = Modifier.width(
                    dimensionResource(R.dimen.spacer_width)
                )
            )

            Text(text = it)
        }
    }
}