package com.rm.postapp.domain.repository

import com.rm.postapp.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Flow<List<Post>>
    suspend fun getPostById(postId: Int): Post
    suspend fun refreshPosts()

}