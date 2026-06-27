package com.rm.postapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.rm.postapp.R

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconDescription: String? = null,
    text: String = ""
) {
    Row(
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription
        )

        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_width)))
            Text(text)
        }

    }
}