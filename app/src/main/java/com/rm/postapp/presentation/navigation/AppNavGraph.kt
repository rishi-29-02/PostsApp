package com.rm.postapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rm.postapp.presentation.screen.postHome.PostScreen
import com.rm.postapp.presentation.screen.postdescription.PostDescriptionScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.PostScreen
    ) {

        composable<NavRoutes.PostScreen> {
            PostScreen { postId ->
                navController.navigate(
                    NavRoutes.PostDescriptionScreen(postId = postId)
                )
            }
        }

        composable<NavRoutes.PostDescriptionScreen> {
            PostDescriptionScreen(navController = navController)
        }


    }

}