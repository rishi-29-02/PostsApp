package com.rm.postapp.domain.usecase

import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import javax.inject.Inject

class GetPostByIDUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId :Int) : Post{
        return postRepository.getPostById(postId)
    }
}