package com.rm.postapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rm.postapp.domain.models.Post
import com.rm.postapp.presentation.screen.PostHome.PostScreen
import com.rm.postapp.presentation.screen.postdescription.PostDescriptionScreen
import kotlinx.serialization.Serializable

@Serializable
object PostScreen

@Serializable
data class PostDescriptionRoute(
    val postId: Int
)

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PostScreen
    ) {

        composable<PostScreen> {
            PostScreen(navController = navController)
        }

        composable<PostDescriptionRoute> { backStackEntry ->
            PostDescriptionScreen(
                navController = navController
            )
        }


    }

}