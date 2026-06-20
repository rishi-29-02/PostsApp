package com.rm.postapp.data.mapper

import com.rm.postapp.data.remote.dto.PostDto
import com.rm.postapp.domain.models.Post

fun PostDto.toDomain(): Post {
    return Post(
        body = body,
        id = id,
        title = title,
        userId = userId
    )
}

fun List<PostDto>.toDomain(): List<Post> = map { it.toDomain() }