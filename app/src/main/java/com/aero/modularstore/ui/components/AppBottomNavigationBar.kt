package com.aero.modularstore.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

enum class NavScreens(val label: String, val route: String) {
    HOME("Modular Store", "home"),
    FAVORITES("Favoritos", "favorites"),
    CART("Carrito", "cart")
}

@Composable
fun AppBottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = NavScreens.HOME.label
                )
            },
            label = { Text(NavScreens.HOME.label) },
            selected = currentRoute == NavScreens.HOME.route,
            onClick = { onNavigate(NavScreens.HOME.route) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = NavScreens.FAVORITES.label
                )
            },
            label = { Text(NavScreens.FAVORITES.label) },
            selected = currentRoute == NavScreens.FAVORITES.route,
            onClick = { onNavigate(NavScreens.FAVORITES.route) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = NavScreens.CART.label
                )
            },
            label = { Text(NavScreens.CART.label) },
            selected = currentRoute == NavScreens.CART.route,
            onClick = { onNavigate(NavScreens.CART.route) }
        )
    }
}

