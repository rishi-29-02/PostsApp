package com.rm.postapp.data.mapper

import com.rm.postapp.data.database.PostEntity
import com.rm.postapp.data.remote.dto.CommentDto
import com.rm.postapp.data.remote.dto.PostDto
import com.rm.postapp.domain.models.Comment
import com.rm.postapp.domain.models.Post

fun PostDto.toDomain(): Post {
    return Post(
        body = body,
        id = id,
        title = title,
        userId = userId
    )
}

fun PostDto.toEntity() = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun PostEntity.toDomain() = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun CommentDto.toDomain() = Comment(
    id = id,
    postId = postId,
    name = name,
    email = email,
    body = body
)

fun List<PostEntity>.toDomain(): List<Post> = map { it.toDomain() }