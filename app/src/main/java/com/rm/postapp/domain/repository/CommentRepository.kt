package com.rm.postapp.domain.repository

import com.rm.postapp.domain.models.Comment

interface CommentRepository {

    suspend fun getComments(
        postId: Int
    ): List<Comment>
}