package com.rm.postapp.utils.exception

sealed class AppException(
    message: String
) : Exception(message) {

    data object NoInternet : AppException("No internet connection")

    data class Unknown(
        override val cause: Throwable?
    ) : AppException("Unknown error")
}