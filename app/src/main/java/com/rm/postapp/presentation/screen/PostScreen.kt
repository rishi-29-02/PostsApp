package com.rm.postapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.postapp.presentation.utils.UiState

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {

    val state by viewModel.postState.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier.padding(top = 10.dp)
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

@Preview(showBackground = true)
@Composable
fun PostHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Posts", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1F))

            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Refresh"
                )
            }
        }

        PostSearch(modifier)
    }
}

@Composable
fun PostSearch(modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = { },
        placeholder = { Text("Search Posts") },

        // 2. Add an ImageVector Icon (combining your previous question)
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Email Icon"
            )
        },
    )

}

