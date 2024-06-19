package com.example.shoppingapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppingapplication.ui.composable.ProductDetailsScreen
import com.example.shoppingapplication.ui.composable.ProductListScreen
import com.example.shoppingapplication.ui.viewmodel.ProductListViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screen.ProductListScreen.route
    ) {
        composable(Screen.ProductListScreen.route) {
            ProductListScreen(navController)
        }
        composable(Screen.ProductDetailScreen.route) {
            ProductDetailsScreen(navController)
        }
    }
}