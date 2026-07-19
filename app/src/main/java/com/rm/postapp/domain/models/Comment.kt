package com.rm.postapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)
