package com.rm.postapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoutes {

    @Serializable
    data object PostScreen

    @Serializable
    data class PostDescriptionScreen(val postId: Int)

    @Serializable
    data object ProfileScreen

    @Serializable
    data object LoginScreen
}