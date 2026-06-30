package com.rm.postapp.presentation.screen.PostHome

import com.rm.postapp.domain.models.Post
import com.rm.postapp.presentation.utils.UiState

data class PostUiState(
    val postState: UiState<List<Post>> = UiState.Loading,
    val searchQuery: String = ""
)