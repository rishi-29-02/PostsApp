package com.rm.postapp.presentation.screen.postHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.usecase.GetAllPostUseCase
import com.rm.postapp.domain.usecase.RefreshPostUseCase
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val refreshPostUseCase: RefreshPostUseCase
) : ViewModel() {

    private var _postState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val postState = _postState.asStateFlow()

    init {
        observePosts()
        refreshPosts()
    }

    private fun observePosts() {
        viewModelScope.launch {

            try {
                getAllPostUseCase().collect { posts ->
                    if (posts.isEmpty()) {
                        _postState.value = UiState.Loading
                    } else {
                        _postState.value = UiState.Success(posts)
                    }
                }

            } catch (e: Exception) {
                _postState.value = UiState.Error(e.toString())
            }
        }
    }

    private fun refreshPosts() {
        viewModelScope.launch {
            refreshPostUseCase()
        }
    }
}
