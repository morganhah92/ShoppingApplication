package com.example.shoppingapplication.data.repository.remote

import com.example.shoppingapplication.data.model.ProductItemModel
import com.example.shoppingapplication.data.model.ProductResponse
import com.example.shoppingapplication.data.remote.APIEndpoints
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow


class APIRepositoryImplTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var apiEndpoints: APIEndpoints

    private lateinit var apiRepository: APIRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        apiRepository = APIRepositoryImpl(apiEndpoints)
    }

    @Test
    fun `getProducts should return product response from API`() = runTest {
        val mockProduct = ProductItemModel(
            id = 1,
            title = "hello",
            description = "description2",
            price = 10.00
        )
        val mockResponse = ProductResponse().apply { add(mockProduct) }


        doReturn(mockResponse).`when`(apiEndpoints).getProducts()

        val result = apiRepository.getProducts()

        assertEquals(mockResponse, result)
    }

    @Test
    fun `getProducts throws exception when API fails`() = runTest {

        val exception = RuntimeException("Network error")
        doThrow(exception).`when`(apiEndpoints).getProducts()

        try {
            apiRepository.getProducts()
            fail("Exception expected")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }


}