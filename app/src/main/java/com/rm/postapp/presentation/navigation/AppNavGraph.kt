package com.rm.postapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rm.postapp.presentation.screen.login.LoginScreen
import com.rm.postapp.presentation.screen.postHome.PostScreen
import com.rm.postapp.presentation.screen.postdescription.PostDescriptionScreen
import com.rm.postapp.presentation.screen.profile.ProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LoginScreen
    ) {

        composable<NavRoutes.PostScreen> {
            PostScreen(
                onPostClick = { postId ->
                navController.navigate(
                    NavRoutes.PostDescriptionScreen(postId = postId)
                )
            },
                onProfileIconClick = {
                    navController.navigate(
                        NavRoutes.ProfileScreen
                    )
                }
            )
        }

        composable<NavRoutes.PostDescriptionScreen> {
            PostDescriptionScreen(navController = navController)
        }

        composable<NavRoutes.ProfileScreen> {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavRoutes.LoginScreen> {
            LoginScreen() {
                navController.navigate(
                    NavRoutes.PostScreen
                )
            }
        }
    }
}