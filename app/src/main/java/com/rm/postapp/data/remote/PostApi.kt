package com.rm.postapp.data.remote

import com.rm.postapp.data.remote.dto.PostDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") postId: Int
    ): PostDto
}