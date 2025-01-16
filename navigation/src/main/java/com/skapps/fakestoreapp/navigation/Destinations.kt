package com.skapps.fakestoreapp.navigation

object HomeDestination {
    const val route = "home"
}

object DetailDestination {
    private const val baseRoute = "detail"
    const val productIdArg = "productId"

    const val route = "$baseRoute/{$productIdArg}"

    fun createRoute(productId: String): String {
        return "$baseRoute/$productId"
    }
}

object BasketDestination {
    const val route = "basket"
}

object FavoriteDestination {
    const val route = "favorite"
}


object CheckoutDestination {
    const val route = "checkout"
}