package com.example.coinmarketcapexchanges.excharges.data.repository

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse
import com.example.coinmarketcapexchanges.excharges.data.TestDataItens
import com.example.coinmarketcapexchanges.excharges.data.remote.ExchangeRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExchangeRepositoryImplTest {

    private lateinit var repository: ExchangeRepositoryImpl
    private val mockDataSource: ExchangeRemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = ExchangeRepositoryImpl(mockDataSource)
    }

    @Test
    fun `getExchanges should return Success with mapped list when remote call succeeds`() =
        runTest {
            // Arrange
            val mockExchangeInfo = TestDataItens.getInfoResponse()

            val remoteResult: ApiResult<ExchangeInfoResponse> = ApiResult.Success(
                mockExchangeInfo
            )

            coEvery { mockDataSource.getExchangeInfo() } returns remoteResult

            // Act
            val result = repository.getExchanges()

            // Assert
            assertTrue(result is ApiResult.Success)
            val successResult = result as ApiResult.Success
            assertEquals(1, successResult.data.size)
            assertEquals("Binance", successResult.data[0].name)
        }

    @Test
    fun `getExchanges should return Error when remote call fails`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        val remoteResult = ApiResult.Error(errorMessage)

        coEvery { mockDataSource.getExchangeInfo() } returns remoteResult

        // Act
        val result = repository.getExchanges()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeDetails should return Success with assets list when remote call succeeds`() =
        runTest {
            // Arrange
            val exchangeId = "binance"
            val mockAsset = TestDataItens.getExchangeAssetResponse()

            val remoteResult = ApiResult.Success(
                mockAsset
            )

            coEvery { mockDataSource.getExchangeAssets(exchangeId) } returns remoteResult

            // Act
            val result = repository.getExchangeDetails(exchangeId)

            // Assert
            assertTrue(result is ApiResult.Success)
            val successResult = result as ApiResult.Success
            assertEquals(3, successResult.data.size)
            assertEquals("0xf977814e90da44bfa03b6295a0616a897441acec", successResult.data[0].walletAddress)
        }

    @Test
    fun `getExchangeDetails should return Error when remote call fails`() = runTest {
        // Arrange
        val exchangeId = "binance"
        val errorMessage = "Not found"
        val remoteResult = ApiResult.Error(errorMessage)

        coEvery { mockDataSource.getExchangeAssets(exchangeId) } returns remoteResult

        // Act
        val result = repository.getExchangeDetails(exchangeId)

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `getExchangeDetails should return empty list when remote returns empty data`() =
        runTest {
            // Arrange
            val exchangeId = "binance"
            val remoteResult = ApiResult.Success(TestDataItens.getEmptyExchangeAssetResponse())

            coEvery { mockDataSource.getExchangeAssets(exchangeId) } returns remoteResult

            // Act
            val result = repository.getExchangeDetails(exchangeId)

            // Assert
            assertTrue(result is ApiResult.Success)
            assertTrue((result as ApiResult.Success).data.isEmpty())
        }
}