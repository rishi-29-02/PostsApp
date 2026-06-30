package com.rm.postapp.presentation.screen.postHome

import com.rm.postapp.domain.models.Post
import com.rm.postapp.presentation.utils.UiState

data class PostUiState(
    val postState: UiState<List<Post>> = UiState.Loading,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)