package com.example.shoppingapplication.data.repository.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.shoppingapplication.data.local.ProductPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductDataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): ProductDataStoreRepository {

    override suspend fun saveProducts(productsJson: String) {
        dataStore.edit { preferences ->
            preferences[ProductPreferencesKey.PRODUCT_LIST] = productsJson

        }
    }

    override fun getProducts(): Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[ProductPreferencesKey.PRODUCT_LIST]
        }
}