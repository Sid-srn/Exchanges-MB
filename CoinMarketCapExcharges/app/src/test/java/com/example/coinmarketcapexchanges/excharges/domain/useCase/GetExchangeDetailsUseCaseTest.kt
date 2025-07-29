package com.example.coinmarketcapexchanges.excharges.domain.useCase

import com.example.coinmarketcapexchanges.TestDataItens
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetExchangeDetailsUseCaseTest {

    private lateinit var useCase: GetExchangeDetailsUseCase
    private val mockRepository: ExchangeRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetExchangeDetailsUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Success with assets when repository call succeeds`() = runBlocking {
        // Arrange
        val exchangeId = "binance"
        val mockAssets = TestDataItens.getExchangeAssetsList()

        coEvery { mockRepository.getExchangeDetails(exchangeId) } returns
                ApiResult.Success(mockAssets)

        // Act
        val result = useCase(exchangeId).first()

        // Assert
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
        assertEquals("0xf977814e90da44bfa03b6295a0616a897441acec", result.data[0].walletAddress)
    }

    @Test
    fun `invoke should emit Error when repository call fails`() = runBlocking {
        // Arrange
        val exchangeId = "binance"
        val errorMessage = "Network error"

        coEvery { mockRepository.getExchangeDetails(exchangeId) } returns
                ApiResult.Error(errorMessage)

        // Act
        val result = useCase(exchangeId).first()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `invoke should emit Error with exception message when exception occurs`() = runBlocking {
        // Arrange
        val exchangeId = "binance"
        val exceptionMessage = "Network failure"
        val exception = RuntimeException(exceptionMessage)

        coEvery { mockRepository.getExchangeDetails(exchangeId) } throws exception

        // Act
        val result = useCase(exchangeId).first()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(exceptionMessage, (result as ApiResult.Error).message)
    }
}