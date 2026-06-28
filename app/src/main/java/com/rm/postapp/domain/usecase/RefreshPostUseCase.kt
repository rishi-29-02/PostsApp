package com.rm.postapp.domain.usecase

import com.rm.postapp.domain.repository.PostRepository
import javax.inject.Inject

class RefreshPostUseCase @Inject constructor(
    private val postRepository: PostRepository
)  {
    suspend operator fun invoke() {
        return postRepository.refreshPosts()
    }
}