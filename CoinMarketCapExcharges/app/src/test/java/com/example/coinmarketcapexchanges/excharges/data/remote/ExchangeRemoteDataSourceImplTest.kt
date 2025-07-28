package com.example.coinmarketcapexchanges.excharges.data.remote

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.core.network.CoinMarketCapApi
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeAssetsResponse
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse
import com.example.coinmarketcapexchanges.excharges.data.TestDataItens
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ExchangeRemoteDataSourceImplTest {

    private lateinit var dataSource: ExchangeRemoteDataSourceImpl
    private val mockApi: CoinMarketCapApi = mockk()

    @Before
    fun setUp() {
        dataSource = ExchangeRemoteDataSourceImpl(mockApi)
    }

    @Test
    fun `getExchangeInfo should return Success when API call is successful`() = runTest {
        // Arrange
        val mockResponse = TestDataItens.getInfoResponse()
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockApi.getExchangeInfo() } returns successfulResponse

        // Act
        val result = dataSource.getExchangeInfo()

        // Assert
        assertTrue(result is ApiResult.Success)
        assertEquals(mockResponse, (result as ApiResult.Success).data)
    }

    @Test
    fun `getExchangeInfo should return Error when API call fails`() = runTest {
        // Arrange
        val errorMessage = "Response.error()"
        val errorResponse = Response.error<ExchangeInfoResponse>(404, ResponseBody.create(null, errorMessage))

        coEvery { mockApi.getExchangeInfo() } returns errorResponse

        // Act
        val result = dataSource.getExchangeInfo()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeInfo should return Error when API call throws exception`() = runTest {
        // Arrange
        val exceptionMessage = "Network error"
        coEvery { mockApi.getExchangeInfo() } throws Exception(exceptionMessage)

        // Act
        val result = dataSource.getExchangeInfo()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(exceptionMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeInfo should return Error when response body is null`() = runTest {
        // Arrange
        val successfulResponse = Response.success<ExchangeInfoResponse>(null)
        coEvery { mockApi.getExchangeInfo() } returns successfulResponse

        // Act
        val result = dataSource.getExchangeInfo()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals("Response body is null", (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeAssets should return Success when API call is successful`() = runTest {
        // Arrange
        val exchangeId = "123"
        val mockResponse = TestDataItens.getExchangeAssetResponse()
        val successfulResponse = Response.success(mockResponse)

        coEvery { mockApi.getExchangeAssets(id = exchangeId) } returns successfulResponse

        // Act
        val result = dataSource.getExchangeAssets(exchangeId)

        // Assert
        assertTrue(result is ApiResult.Success)
        assertEquals(mockResponse, (result as ApiResult.Success).data)
    }

    @Test
    fun `getExchangeAssets should return Error when API call fails`() = runTest {
        // Arrange
        val exchangeId = "123"
        val errorMessage = "Response.error()"
        val errorResponse = Response.error<ExchangeAssetsResponse>(404, ResponseBody.create(null, errorMessage))

        coEvery { mockApi.getExchangeAssets(id = exchangeId) } returns errorResponse

        // Act
        val result = dataSource.getExchangeAssets(exchangeId)

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeAssets should return Error when API call throws exception`() = runTest {
        // Arrange
        val exchangeId = "123"
        val exceptionMessage = "Network error"
        coEvery { mockApi.getExchangeAssets(id = exchangeId) } throws Exception(exceptionMessage)

        // Act
        val result = dataSource.getExchangeAssets(exchangeId)

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(exceptionMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeAssets should return Error when response body is null`() = runTest {
        // Arrange
        val exchangeId = "123"
        val successfulResponse = Response.success<ExchangeAssetsResponse>(null)
        coEvery { mockApi.getExchangeAssets(id = exchangeId) } returns successfulResponse

        // Act
        val result = dataSource.getExchangeAssets(exchangeId)

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals("Response body is null", (result as ApiResult.Error).message)
    }

}