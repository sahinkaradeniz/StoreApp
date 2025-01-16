package com.skapps.fakestoreapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomNavBar(navController: NavController, basketCount: Int) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == HomeDestination.route,
            onClick = { navController.navigate(HomeDestination.route) },
            icon = { Icon(Icons.Default.Home , contentDescription = "Home") },
            label = { Text(text = "Home") }
        )
        NavigationBarItem(
            selected = currentRoute == FavoriteDestination.route,
            onClick = { navController.navigate(FavoriteDestination.route) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite") },
            label = { Text(text = "Favorite") }
        )
        NavigationBarItem(
            selected = currentRoute == BasketDestination.route,
            onClick = { navController.navigate(BasketDestination.route) },
            icon = {
                BadgedBox(
                    badge = {
                        if (basketCount > 0) {
                            Badge { Text(basketCount.toString()) }
                        }
                    }
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Basket")
                }
            },
            label = { Text("Basket") }
        )
    }
}