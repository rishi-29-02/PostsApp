package com.rm.postapp.domain.repository

import com.rm.postapp.domain.models.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}