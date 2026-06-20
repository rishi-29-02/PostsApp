package com.rm.postapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.postapp.presentation.utils.UiState

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {

    val state by viewModel.postState.collectAsStateWithLifecycle()

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
                                    .padding(vertical = 16.dp),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = post.title
                                )
                            }
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