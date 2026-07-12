package com.rm.postapp.domain.usecase

import com.rm.postapp.domain.utils.NetworkMonitor
import com.rm.postapp.utils.NoInternetException
import javax.inject.Inject

class RefreshPostUseCase @Inject constructor(
    private val networkMonitor: NetworkMonitor
)  {
    operator fun invoke() : Result<Unit> {
        return if (networkMonitor.isConnected()) {
            Result.success(Unit)
        } else {
            Result.failure(NoInternetException())
        }
    }
}