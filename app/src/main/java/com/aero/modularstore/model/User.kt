package com.aero.modularstore.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val favoriteProductIds: List<Int> = emptyList()
)

