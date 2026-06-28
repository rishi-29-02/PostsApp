package com.rm.postapp.presentation.screen.PostHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private var _postState = MutableStateFlow(PostUiState())
    val postState = _postState.asStateFlow()

    private var cachePostList = emptyList<Post>()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _postState.update { it.copy(postState = UiState.Loading) }

            delay(2000.milliseconds)

            try {
                val postList = postRepository.getPosts()
                cachePostList = postList
                _postState.update { it.copy(postState = UiState.Success(postList)) }

            } catch (e: Exception) {
                _postState.update { it.copy(postState = UiState.Error(e.toString())) }
            }
        }
    }

    fun onSearchTextQueryChange(searchQuery: String) {
        if (_postState.value.postState is UiState.Success) {
            _postState.update { currentState ->
                val filteredList = cachePostList.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                            || it.body.contains(searchQuery, ignoreCase = true)
                            || it.userId.toString().contains(searchQuery)
                }

                currentState.copy(
                    searchQuery = searchQuery,
                    postState = (currentState.postState as UiState.Success).copy(data = filteredList)
                )
            }
        }
    }
}