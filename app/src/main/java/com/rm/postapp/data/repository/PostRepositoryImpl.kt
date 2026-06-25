package com.rm.postapp.data.repository

import com.rm.postapp.data.mapper.toDomain
import com.rm.postapp.data.remote.PostApi
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
): PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts().toDomain()
    }
    override suspend fun getPostById(postId: Int): Post {
        return postApi.getPostById(postId).toDomain()
    }
}