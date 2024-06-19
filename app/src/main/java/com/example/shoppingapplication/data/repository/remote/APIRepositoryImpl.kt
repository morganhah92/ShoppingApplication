package com.example.shoppingapplication.data.repository.remote

import com.example.shoppingapplication.data.model.ProductResponse
import com.example.shoppingapplication.data.remote.APIEndpoints
import javax.inject.Inject

class APIRepositoryImpl @Inject constructor(
    private val apiEndPoints: APIEndpoints
): APIRepository {

    override suspend fun getProducts(): ProductResponse = apiEndPoints.getProducts()

}