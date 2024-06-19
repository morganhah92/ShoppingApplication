package com.example.shoppingapplication.data.repository.remote

import com.example.shoppingapplication.data.model.ProductResponse

interface APIRepository {

    suspend fun getProducts(): ProductResponse
}