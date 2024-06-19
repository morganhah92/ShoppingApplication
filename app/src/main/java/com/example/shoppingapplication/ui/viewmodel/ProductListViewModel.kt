package com.example.shoppingapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapplication.data.model.ProductItemModel
import com.example.shoppingapplication.data.model.ProductResponse
import com.example.shoppingapplication.data.repository.local.ProductDataStoreRepository
import com.example.shoppingapplication.data.repository.remote.APIRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: APIRepository,
    private val dataStore: ProductDataStoreRepository
) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductItemModel>>(emptyList())
    val productList: StateFlow<List<ProductItemModel>> get() = _productList

    private val _selectedProduct = MutableStateFlow<ProductItemModel?>(null)
    val selectedProduct: StateFlow<ProductItemModel?> get() = _selectedProduct

    init {
        fetchProducts()
    }


    private fun fetchProducts() {
        viewModelScope.launch {
            dataStore.getProducts().collect { productsJson ->
                if (productsJson.isNullOrEmpty()) {
                    val fetchedProducts = repository.getProducts()
                    _productList.value = fetchedProducts
                    saveProductsToCache(fetchedProducts)
                } else {
                    val products = parseProductsFromJson(productsJson)
                    _productList.value = products
                }
            }
        }
    }
    private suspend fun saveProductsToCache(products: List<ProductItemModel>) {
        val productsJson = convertProductsToJson(products)
        dataStore.saveProducts(productsJson)
    }

    private fun convertProductsToJson(products: List<ProductItemModel>): String {
        return Gson().toJson(products)
    }

    private fun parseProductsFromJson(productsJson: String): List<ProductItemModel> {
        val listType = object : TypeToken<List<ProductItemModel>>() {}.type
        return Gson().fromJson(productsJson, listType)
    }

    fun selectProduct(product: ProductItemModel) {
        _selectedProduct.value = product
    }

}