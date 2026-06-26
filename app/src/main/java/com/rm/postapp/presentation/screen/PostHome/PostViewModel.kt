package com.rm.postapp.presentation.screen.PostHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private var _postState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val postState = _postState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var cachePostList = emptyList<Post>()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _postState.value = UiState.Loading

            try {
                val postList = postRepository.getPosts()
                cachePostList = postList
                _postState.value = UiState.Success(postList)

            } catch (e: Exception) {
                _postState.value = UiState.Error(e.toString())
            }
        }
    }

    fun onSearchTextQueryChange(searchQuery: String) {
        _searchQuery.value = searchQuery

        if (_postState.value is UiState.Success) {
            _postState.update { currentState ->
                val filteredList = cachePostList.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                            || it.body.contains(searchQuery, ignoreCase = true)
                            || it.userId.toString().contains(searchQuery)
                }

                (currentState as UiState.Success).copy(
                    data = filteredList
                )
            }
        }
    }
}