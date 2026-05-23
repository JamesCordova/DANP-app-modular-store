package com.aero.modularstore.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aero.modularstore.ui.components.AppBottomNavigationBar
import com.aero.modularstore.ui.components.NavScreens
import com.aero.modularstore.ui.components.AppToolbar
import com.aero.modularstore.ui.screens.favorites.FavoritesScreen
import com.aero.modularstore.ui.screens.home.HomeScreen
import com.aero.modularstore.ui.screens.home.HomeViewModel
import com.aero.modularstore.ui.screens.productDetail.DetailScreen

@Composable
fun AppNavigation(
    onThemeChange: (String) -> Unit
) {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    var currentRoute: String? by rememberSaveable { mutableStateOf(NavScreens.HOME.route) }

    Scaffold(
        topBar = {
            when (currentRoute) {
                NavScreens.HOME.route -> AppToolbar(title = NavScreens.HOME.label)
                NavScreens.FAVORITES.route -> AppToolbar(title = NavScreens.FAVORITES.label)
                null -> { /* detail route: show DetailHeader via NavHost composable state below using current product id */ }
                else -> AppToolbar(title = "")
            }
        },
        bottomBar = {
            // No mostrar BottomBar en DetailScreen
            if (!isDetailScreen(currentRoute)) {
                AppBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        currentRoute = route
                    }
                )
            }
        }
    ) { paddingValues ->
        val navModifier = if (isDetailScreen(currentRoute)) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        }

        NavHost(
            navController = navController,
            startDestination = NavScreens.HOME.route,
            modifier = navModifier
        ) {
            composable(NavScreens.HOME.route) {
                currentRoute = NavScreens.HOME.route
                HomeScreen(
                    navController,
                    onThemeChange,
                    homeViewModel
                )
            }
            composable(NavScreens.FAVORITES.route) {
                currentRoute = NavScreens.FAVORITES.route
                FavoritesScreen(
                    navController,
                    homeViewModel
                )
            }
            composable(
                "detail/{productId}"
            ) { backStack ->
                val productId =
                    backStack.arguments?.getString("productId")?.toIntOrNull() ?: 0
                currentRoute = null
                // For detail route, replace the topBar with DetailHeader that needs product info and favorite state.
                // Since topBar lambda cannot access productId here easily, we will draw the header inside DetailScreen's content area at top.
                DetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() },
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

private fun isDetailScreen(route: String?): Boolean {
    return route == null || route.startsWith("detail/")
}
