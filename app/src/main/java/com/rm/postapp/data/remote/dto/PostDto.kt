package com.rm.postapp.data.remote.dto

import com.rm.postapp.domain.models.Post

data class PostDto(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)