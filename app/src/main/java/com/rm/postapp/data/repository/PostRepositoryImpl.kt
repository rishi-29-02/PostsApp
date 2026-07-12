package com.rm.postapp.data.repository

import com.rm.postapp.data.database.PostDao
import com.rm.postapp.data.mapper.toDomain
import com.rm.postapp.data.mapper.toEntity
import com.rm.postapp.data.remote.PostApi
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.domain.utils.NetworkMonitor
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao,
    private val networkMonitor: NetworkMonitor
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        if (networkMonitor.isConnected()) {
            val remotePosts = postApi.getPosts()
            postDao.insertPost(remotePosts.map { it.toEntity() })
        }
        return postDao.getAllPost().toDomain()
    }

    override suspend fun getPostById(postId: Int): Post {
        return postDao.getPostID(postId).toDomain()
    }
}
