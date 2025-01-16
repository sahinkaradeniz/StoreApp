package com.skapps.fakestoreapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = HomeDestination.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        homeScreen(
            onNavigateToDetail = { productId ->
                navController.navigate(DetailDestination.createRoute(productId))
            }
        )
        detailScreen(navController = navController)

        basketScreen(
            onNavigateToCheckout = {
                navController.navigate(CheckoutDestination.route)
            }
        )

        favoriteScreen(
            onNavigateToDetail = { productId ->
                navController.navigate(DetailDestination.createRoute(productId))
            }
        )

        checkoutScreen(
            onNavigateToHome = {
                navController.navigate(HomeDestination.route) {
                    popUpTo(HomeDestination.route) {
                        inclusive = true
                    }
                }
            },
            onNavigateToBasket = {
                navController.navigate(BasketDestination.route)
            }
        )
    }
}