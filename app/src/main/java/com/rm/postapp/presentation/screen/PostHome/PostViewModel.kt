package com.rm.postapp.presentation.screen.postHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
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
    private val postRepository: PostRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private var _postState = MutableStateFlow(PostUiState())
    val postState = _postState.asStateFlow()

    private var cachePostList = emptyList<Post>()

    init {
        getPosts(false)
    }

    fun getPosts(isRefresh: Boolean) {
        if (isRefresh) {
            _postState.update { it.copy(isRefreshing = true) }
        } else {
            _postState.update { it.copy(postState = UiState.Loading) }
        }

        if (networkMonitor.isConnected()) {
            viewModelScope.launch {

                delay(2000.milliseconds)

                try {
                    val postList = postRepository.getPosts()
                    cachePostList = postList
                    _postState.update { it.copy(postState = UiState.Success(postList), isRefreshing = false) }

                } catch (e: Exception) {
                    if (isRefresh) {
                        _postState.update { it.copy(errorMessage = e.toString(), isRefreshing = false) }
                    } else {
                        _postState.update { it.copy(postState = UiState.Error(e.toString()), errorMessage = e.toString(), isRefreshing = false) }
                    }
                }
            }
        } else {
            if (isRefresh) {
                _postState.update { it.copy(errorMessage = "No Internet", isRefreshing = false) }
            } else {
                _postState.update { it.copy(postState = UiState.Error("No Internet"), errorMessage = "No Internet", isRefreshing = false) }
            }
        }
    }

    fun dismissError() {
        _postState.update { it.copy(errorMessage = null) }
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