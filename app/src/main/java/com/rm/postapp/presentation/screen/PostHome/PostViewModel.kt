package com.rm.postapp.presentation.screen.PostHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private var _postState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val postState = _postState.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _postState.value = UiState.Loading

            try {
                val postList = postRepository.getPosts()
                _postState.value = UiState.Success(postList)

            } catch (e: Exception) {
                _postState.value = UiState.Error(e.toString())
            }
        }
    }
}