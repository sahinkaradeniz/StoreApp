package com.skapps.fakestoreapp.navigation

import com.skapps.checkout.CheckoutScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skapps.basket.BasketScreen
import com.skapps.detail.ProductDetailScreen
import com.skapps.favorites.FavoritesScreen
import com.skapps.home.HomeScreen

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (String) -> Unit
) {
    composable(route = HomeDestination.route) {
        HomeScreen(
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

fun NavGraphBuilder.detailScreen(navController: NavController) {
    composable(
        route = DetailDestination.route,
        arguments = listOf(
            navArgument(DetailDestination.productIdArg) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString(DetailDestination.productIdArg)
        ProductDetailScreen(productId = productId ?: "", navController = navController)
    }
}

fun NavGraphBuilder.basketScreen(
    onNavigateToCheckout: () -> Unit
) {
    composable(route = BasketDestination.route) {
        BasketScreen(
            onNavigateToCheckout = onNavigateToCheckout
        )
    }
}

fun NavGraphBuilder.favoriteScreen(
    onNavigateToDetail: (String) -> Unit
) {
    composable(route = FavoriteDestination.route) {
        FavoritesScreen(
            onNavigateToProductDetail = onNavigateToDetail
        )
    }
}

fun NavGraphBuilder.checkoutScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToBasket: () -> Unit
) {
    composable(route = CheckoutDestination.route) {
         CheckoutScreen(
             finish = onNavigateToHome,
                onBack = onNavigateToBasket
         )
    }
}