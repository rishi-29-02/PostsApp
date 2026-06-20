package com.rm.postapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rm.postapp.R

@Composable
fun PostContent(
    modifier: Modifier,
    title: String,
    content: String,
    userID: Int,
    postID: Int
) {
    Row(modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        UserProfileIcon(modifier, "U$userID")
        Spacer(modifier = modifier.width(dimensionResource(R.dimen.spacer_width)))


        Column(modifier.weight(1f)) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = title,
                fontSize = 20.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = modifier.fillMaxWidth(),
                text = content,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "User $userID • Post #$postID",
                fontSize = 11.sp,
                color = Color.Gray
            )

        }

    }
}


@Composable
fun UserProfileIcon(
    modifier: Modifier,
    user: String
) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.user_profile_icon_size))
            .clip(CircleShape)
            .background(Color(0xFF1565C0)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = user,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}