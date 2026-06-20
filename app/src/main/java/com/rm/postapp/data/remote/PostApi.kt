package com.rm.postapp.data.remote

import com.rm.postapp.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): List<PostDto>
}