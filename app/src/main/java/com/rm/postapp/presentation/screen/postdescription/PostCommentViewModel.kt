package com.rm.postapp.presentation.screen.postdescription

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rm.postapp.domain.models.Comment
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.usecase.GetPostCommentsUseCase
import com.rm.postapp.presentation.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostCommentViewModel @Inject constructor(
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val postId =
        savedStateHandle.toRoute<NavRoutes.PostDescriptionScreen>().postId
    private val _comments =
        MutableStateFlow<List<Comment>>(emptyList())

    val comments = _comments.asStateFlow()
    init {
        fetchComments(postId)
    }

    fun fetchComments(postId: Int){
        viewModelScope.launch {
            _comments.value = getPostCommentsUseCase(postId)
        }

    }
}