package com.rm.postapp.presentation.screen.postHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.usecase.GetAllPostUseCase
import com.rm.postapp.domain.usecase.RefreshPostUseCase
import com.rm.postapp.domain.utils.NetworkMonitor
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val refreshPostUseCase: RefreshPostUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private var _postState = MutableStateFlow(PostUiState())
    val postState = _postState.asStateFlow()

    private var cachePostList = emptyList<Post>()

    init {
        observePosts()
        getPosts(false)
    }

    private fun observePosts() {
        viewModelScope.launch {
            try {
                getAllPostUseCase().collect { posts ->
                    cachePostList = posts
                    if (posts.isEmpty()) {
                        _postState.update { it.copy(postState = UiState.Loading) }
                    } else {
                        val filteredList = filterPosts(posts, _postState.value.searchQuery)
                        _postState.update { it.copy(postState = UiState.Success(filteredList)) }
                    }
                }
            } catch (e: Exception) {
                _postState.update { it.copy(postState = UiState.Error(e.toString())) }
            }
        }
    }

    fun getPosts(isRefresh: Boolean) {
        if (isRefresh) {
            _postState.update { it.copy(isRefreshing = true) }
        } else {
            if (cachePostList.isEmpty()) {
                _postState.update { it.copy(postState = UiState.Loading) }
            }
        }

        if (networkMonitor.isConnected()) {
            viewModelScope.launch {
                delay(2000.milliseconds)
                try {
                    refreshPostUseCase()
                    _postState.update { it.copy(isRefreshing = false) }
                } catch (e: Exception) {
                    _postState.update {
                        it.copy(
                            errorMessage = e.toString(),
                            isRefreshing = false,
                            postState = if (cachePostList.isEmpty()) UiState.Error(e.toString()) else it.postState
                        )
                    }
                }
            }
        } else {
            val errorMsg = "No Internet"
            _postState.update {
                it.copy(
                    errorMessage = errorMsg,
                    isRefreshing = false,
                    postState = if (cachePostList.isEmpty()) UiState.Error(errorMsg) else it.postState
                )
            }
        }
    }

    fun dismissError() {
        _postState.update { it.copy(errorMessage = null) }
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
}
