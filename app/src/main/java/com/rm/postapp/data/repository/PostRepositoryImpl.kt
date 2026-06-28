package com.rm.postapp.data.repository

import android.util.Log
import com.rm.postapp.data.database.PostDao
import com.rm.postapp.data.mapper.toDomain
import com.rm.postapp.data.mapper.toEntity
import com.rm.postapp.data.remote.PostApi
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao
) : PostRepository {

    override suspend fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPost().map { list ->
            list.map { it.toDomain() }
        }

    }

    override suspend fun refreshPosts() {

        try {
            val remotePosts = postApi.getPosts()

            postDao.insertPost(
                remotePosts.map { it.toEntity() }
            )

        } catch (e: Exception) {
            Log.e("Repository", e.message ?: "")
        }
    }

    override suspend fun getPostById(postId: Int): Post {
        return postDao.getPostID(postId).toDomain()
    }
}
