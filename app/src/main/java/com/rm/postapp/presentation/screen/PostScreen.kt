package com.rm.postapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.postapp.presentation.utils.UiState

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {
    val state by viewModel.postState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        PostHeader()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when(state) {
                UiState.Loading -> {
                    CircularProgressIndicator(
                        Modifier
                            .align(Alignment.Center)
                    )
                }
                is UiState.Success<*> -> {
                    val posts = (state as UiState.Success).data
                    LazyColumn {
                        items(posts) { post ->
                            Card(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {

                                PostContent(
                                    modifier = Modifier,
                                    title = post.title,
                                    content = post.body,
                                    userID = post.userId,
                                    postID = post.id
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).message
                    )
                }
            }
        }
    }
}

@Composable
fun PostHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.PostAdd,
                contentDescription = "Post Icons"
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Posts",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1F))

            IconButton(
                onClick = {

                }
            ) {
                UserProfileIcon()
            }
        }

        PostSearch(modifier.padding(top = 4.dp))
    }
}

@Composable
fun UserProfileIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("RM", color = Color.DarkGray)
    }
}

@Composable
fun PostSearch(modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = "",
        onValueChange = { },
        placeholder = { Text("Search Posts") },
        shape = CircleShape,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Email Icon"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    )
}