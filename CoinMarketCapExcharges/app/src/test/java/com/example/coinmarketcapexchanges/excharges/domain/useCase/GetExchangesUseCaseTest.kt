package com.example.coinmarketcapexchanges.excharges.domain.useCase

import com.example.coinmarketcapexchanges.TestDataItens
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetExchangesUseCaseTest {

    private lateinit var useCase: GetExchangesUseCase
    private val mockRepository: ExchangeRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetExchangesUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Success with exchanges list when repository call succeeds`() = runBlocking {
        // Arrange
        val mockExchanges = TestDataItens.getExchangeInfo()

        coEvery { mockRepository.getExchanges() } returns ApiResult.Success(mockExchanges)

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result is ApiResult.Success)
        val successResult = result as ApiResult.Success
        assertEquals(1, successResult.data.size)
        assertEquals("Binance", successResult.data[0].name)
    }

    @Test
    fun `invoke should emit Error when repository call fails`() = runBlocking {
        // Arrange
        val errorMessage = "Network error"

        coEvery { mockRepository.getExchanges() } returns ApiResult.Error(errorMessage)

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }

    @Test
    fun `invoke should emit empty list when repository returns empty list`() = runBlocking {
        // Arrange
        coEvery { mockRepository.getExchanges() } returns ApiResult.Success(emptyList())

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).data.isEmpty())
    }

    @Test
    fun `invoke should emit Error with exception message when exception occurs`() = runBlocking {
        // Arrange
        val exceptionMessage = "Database failure"
        val exception = RuntimeException(exceptionMessage)

        coEvery { mockRepository.getExchanges() } throws exception

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result is ApiResult.Error)
        assertEquals(exceptionMessage, (result as ApiResult.Error).message)
    }
}