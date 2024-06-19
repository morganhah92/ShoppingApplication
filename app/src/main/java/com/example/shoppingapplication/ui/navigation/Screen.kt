package com.example.shoppingapplication.ui.navigation

sealed class Screen(val route: String) {

    object ProductListScreen: Screen("productList")

    object ProductDetailScreen: Screen("productDetail")
}