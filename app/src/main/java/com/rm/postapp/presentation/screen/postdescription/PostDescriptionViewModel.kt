package com.rm.postapp.presentation.screen.postdescription

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.domain.repository.PostShareManager
import com.rm.postapp.domain.usecase.GetPostByIDUseCase
import com.rm.postapp.presentation.navigation.NavRoutes
import com.rm.postapp.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostDescriptionViewModel @Inject constructor(
    private val getPostByIDUseCase: GetPostByIDUseCase,
    private val postShareManager: PostShareManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val postId =
        savedStateHandle.toRoute<NavRoutes.PostDescriptionScreen>().postId
    private val _postState =
        MutableStateFlow<UiState<Post>>(UiState.Loading)

    val postDescriptionState: StateFlow<UiState<Post>>
        get() = _postState

    init {
        fetchPost(postId)
    }

    fun fetchPost(postId: Int) {

        viewModelScope.launch {

            _postState.value = UiState.Loading

            try {

                _postState.value = UiState.Success(getPostByIDUseCase(postId))

            } catch (e: Exception) {

                _postState.value =
                    UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun sharePost() {
        if (postDescriptionState.value is UiState.Success) {
            postShareManager.sharePost((postDescriptionState.value as UiState.Success).data)
        }
    }
}