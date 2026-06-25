package com.rm.postapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rm.postapp.R

@Composable
fun UserProfileIcon(
    modifier: Modifier,
    user: String,
    textColor: Color,
    backgroundColor: Color,

) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.user_profile_icon_size))
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = user,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}