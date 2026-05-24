package com.aero.modularstore.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: ProductCategory,
    val imageUrl: String,
)
