package com.example.shoppingapplication.ui.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ui.navigation.Screen
import com.example.shoppingapplication.ui.navigation.TopNavigationBar
import com.example.shoppingapplication.ui.viewmodel.ProductListViewModel

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel(navController.previousBackStackEntry!!)
) {

    val product by viewModel.selectedProduct.collectAsState()

    Scaffold(
        topBar = {
            TopNavigationBar(
                title = "Product Details",
                onNavigationIconClick = {
                    navController.navigate(Screen.ProductListScreen.route)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product?.image),
                contentDescription = product?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Text(
                text = product?.title ?: "No title",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Category: ${product?.category}"
            )
            Text(
                text = "Description: ${product?.description}",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${product?.rating?.rate}/5"
                )
                Image(
                    painter = painterResource(R.drawable.star_24dp_fill0_wght400_grad0_opsz24),
                    colorFilter = ColorFilter.tint(Color(0xFFFFA500)),
                    contentDescription = null
                )
                Text(
                    text = "out of ${product?.rating?.count} reviews"
                )
            }
            Text(
                text = "Price: $${product?.price}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
