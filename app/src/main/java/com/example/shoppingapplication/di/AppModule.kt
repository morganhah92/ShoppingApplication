package com.example.shoppingapplication.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.shoppingapplication.data.remote.APIDetails
import com.example.shoppingapplication.data.remote.APIEndpoints
import com.example.shoppingapplication.data.repository.local.ProductDataStoreRepository
import com.example.shoppingapplication.data.repository.local.ProductDataStoreRepositoryImpl
import com.example.shoppingapplication.data.repository.remote.APIRepository
import com.example.shoppingapplication.data.repository.remote.APIRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = Gson()
        val gsonConverterFactory = GsonConverterFactory.create(gson)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(APIDetails.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIEndpoints {
        return retrofit.create(APIEndpoints::class.java)
    }

    @Provides
    fun provideRepository(apiEndpoints: APIEndpoints): APIRepository {
        return APIRepositoryImpl(apiEndpoints)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.dataStoreFile("settings.preferences_pb") }
        )
    }

    @Provides
    @Singleton
    fun provideAgentDataStoreRepository(
        dataStore: DataStore<Preferences>
    ): ProductDataStoreRepository {
        return ProductDataStoreRepositoryImpl(dataStore)
    }
}