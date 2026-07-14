package com.rm.postapp.domain.usecase

import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPostUseCase @Inject constructor(private val postRepository: PostRepository) {

    suspend operator fun invoke(): List<Post> {
       return  postRepository.getPosts()
    }
}