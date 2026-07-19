package com.rm.postapp.data.repository

import com.rm.postapp.data.database.PostDao
import com.rm.postapp.data.mapper.toDomain
import com.rm.postapp.data.mapper.toEntity
import com.rm.postapp.data.remote.PostApi
import com.rm.postapp.domain.models.Comment
import com.rm.postapp.domain.repository.CommentRepository
import com.rm.postapp.domain.utils.NetworkMonitor
import com.rm.postapp.utils.exception.AppException
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val networkMonitor: NetworkMonitor
) : CommentRepository {

    override suspend fun getComments(postId: Int): List<Comment> {
        if (!networkMonitor.isConnected()) {
            throw AppException.NoInternet
        }
        return postApi.getPostComments(postId)
            .map { it.toDomain() }
    }
}