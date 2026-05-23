package com.aero.modularstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aero.modularstore.ui.screens.productDetail.DetailScreen
import com.aero.modularstore.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    onThemeChange: (String) -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController,
                onThemeChange
            )
        }
        composable(
            "detail/{productId}"
        ) { backStack ->
            val productId =
                backStack.arguments?.getString("productId")?.toIntOrNull() ?: 0
            DetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}