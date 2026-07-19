package com.rm.postapp.domain.usecase

import com.rm.postapp.domain.models.Comment
import com.rm.postapp.domain.repository.CommentRepository
import jakarta.inject.Inject

class GetPostCommentsUseCase @Inject constructor(
    private val repository: CommentRepository
) {

    suspend operator fun invoke(
        postId: Int
    ): List<Comment> {

        return repository.getComments(postId)
    }
}