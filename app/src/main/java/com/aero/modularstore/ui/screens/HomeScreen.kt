package com.aero.modularstore.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aero.modularstore.model.Product
import com.aero.modularstore.ui.components.AppToolbar
import com.aero.modularstore.ui.components.ProductCard
import com.aero.modularstore.ui.components.ThemeSelector

@Composable
fun HomeScreen(
    navController: NavController,
    onThemeChange: (String) -> Unit
) {
    val products = remember {
        listOf(
            Product(
                1,
                "Laptop Gamer",
                "RTX 4070 + Ryzen 9",
                2500.0
            ),
            Product(
                2,
                "Mechanical Keyboard",
                "RGB Switch Blue",
                120.0
            ),
            Product(
                3,
                "Gaming Mouse",
                "16000 DPI",
                75.0
            )
        )
    }
    Column {
        AppToolbar(
            title = "Modular Store"
        )
        Spacer(modifier = Modifier.height(12.dp))
        ThemeSelector {
            onThemeChange(it.name)
        }
        LazyColumn {
            items(products) { product ->
                ProductCard(
                    product = product
                ) {
                    navController.navigate(
                        "detail/${it.name}/${it.price}"
                    )
                }
            }
        }
    }
}