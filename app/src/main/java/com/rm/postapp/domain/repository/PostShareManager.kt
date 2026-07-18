package com.rm.postapp.domain.repository

import com.rm.postapp.domain.models.Post

interface PostShareManager {
    fun sharePost(post: Post)
}