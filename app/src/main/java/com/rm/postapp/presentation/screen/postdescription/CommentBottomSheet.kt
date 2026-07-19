package com.rm.postapp.presentation.screen.postdescription

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rm.postapp.domain.models.Comment
import com.rm.postapp.presentation.components.UserProfileIcon

@Composable
fun CommentBottomSheet(
    comments: List<Comment>,
    onDismiss: () -> Unit
) {
    var expandedComments by remember {
        mutableStateOf(setOf<Int>())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Comments (${comments.size})",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = comments,
                key = { it.id }
            ) { comment ->
                val expanded = comment.id in expandedComments

                var isOverflowing by remember(comment.id) {
                    mutableStateOf(false)
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            UserProfileIcon(
                                modifier = Modifier,
                                user = comment.name.first().uppercase(),
                                textColor = Color.White,
                                backgroundColor = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.width(12.dp))

                            Column {

                                Text(
                                    text = comment.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = comment.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = comment.body,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = TextOverflow.Ellipsis,
                            onTextLayout = {
                                isOverflowing = it.hasVisualOverflow
                            }
                        )

                        if (isOverflowing || expanded) {
                            Text(
                                text = if (expanded) "Read less" else "Read more",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable {
                                    expandedComments =
                                        if (expanded) {
                                            expandedComments - comment.id
                                        } else {
                                            expandedComments + comment.id
                                        }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}