package com.example.shoppingapplication.data.repository.local

import kotlinx.coroutines.flow.Flow

interface ProductDataStoreRepository {

    suspend fun saveProducts(productsJson: String)

    fun getProducts(): Flow<String?>
}