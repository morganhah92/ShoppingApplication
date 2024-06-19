package com.example.shoppingapplication.data.remote

import com.example.shoppingapplication.data.model.ProductResponse
import retrofit2.http.GET

interface APIEndpoints {

    @GET(APIDetails.PRODUCTS_ENDPOINT)
    suspend fun getProducts(): ProductResponse

}