package com.rm.postapp.presentation.screen.postHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.usecase.GetAllPostUseCase
import com.rm.postapp.domain.usecase.RefreshPostUseCase
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val refreshPostUseCase: RefreshPostUseCase
) : ViewModel() {

    private var _postState = MutableStateFlow(PostUiState())
    val postState = _postState.asStateFlow()

    private var cachePostList = emptyList<Post>()

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            _postState.update { it.copy(postState = UiState.Loading) }
            delay(2000.milliseconds)
            try {
                cachePostList = getAllPostUseCase()
                _postState.update {
                    it.copy(
                        postState = UiState.Success(cachePostList),
                        isRefreshing = false
                    )
                }
            } catch (e: Exception) {
                _postState.update {
                    it.copy(
                        errorMessage = e.toString(),
                        isRefreshing = false,
                    )
                }
            }
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _postState.update {
                it.copy(isRefreshing = true)
            }

            withContext(Dispatchers.IO) {
                refreshPostUseCase()
                    .onSuccess {
                        _postState.update {
                            it.copy(isRefreshing = false)
                        }
                        getPosts()
                    }
                .onFailure { exception ->
                    _postState.update {
                        it.copy(
                            errorMessage = exception.message ?: "An unknown error occurred",
                            isRefreshing = false
                        )
                    }
                }
            }
        }
    }

    fun onSearchTextQueryChange(searchQuery: String) {
        _postState.update { currentState ->
            val filteredList = filterPosts(cachePostList, searchQuery)
            currentState.copy(
                searchQuery = searchQuery,
                postState = if (currentState.postState is UiState.Success || cachePostList.isNotEmpty()) {
                    UiState.Success(filteredList)
                } else {
                    currentState.postState
                }
            )
        }
    }

    private fun filterPosts(posts: List<Post>, query: String): List<Post> {
        if (query.isBlank()) return posts
        return posts.filter {
            it.title.contains(query, ignoreCase = true)
                    || it.body.contains(query, ignoreCase = true)
                    || it.userId.toString().contains(query)
        }
    }

    fun dismissError() {
        _postState.update { it.copy(errorMessage = null) }
    }
}
