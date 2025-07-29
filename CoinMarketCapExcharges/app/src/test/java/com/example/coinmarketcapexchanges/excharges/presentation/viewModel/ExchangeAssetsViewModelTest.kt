package com.example.coinmarketcapexchanges.excharges.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coinmarketcapexchanges.TestDataItens
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangeDetailsUseCase
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeDetailState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ExchangeAssetsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val mockGetExchangeDetailsUseCase: GetExchangeDetailsUseCase = mockk()
    private lateinit var viewModel: ExchangeAssetsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ExchangeAssetsViewModel(mockGetExchangeDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Assert
        assertEquals(ExchangeDetailState.Loading, viewModel.state.value)
    }

    @Test
    fun `loadAssets should emit Success state when use case returns data`() = runTest {
        // Arrange
        val exchangeId = "binance"
        val mockAssets = TestDataItens.getExchangeAssetsList()

        coEvery { mockGetExchangeDetailsUseCase(exchangeId) } returns flowOf(ApiResult.Success(mockAssets))

        val states = mutableListOf<ExchangeDetailState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadAssets(exchangeId)
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeDetailState.Loading, states[0])
        assertTrue(states[1] is ExchangeDetailState.Success)
        assertEquals(mockAssets, (states[1] as ExchangeDetailState.Success).details)
        job.cancel()
    }

    @Test
    fun `loadAssets should emit Error state when use case returns error`() = runTest {
        // Arrange
        val exchangeId = "binance"
        val errorMessage = "Network error"
        coEvery { mockGetExchangeDetailsUseCase(exchangeId) } returns flowOf(ApiResult.Error(errorMessage))

        val states = mutableListOf<ExchangeDetailState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadAssets(exchangeId)
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeDetailState.Loading, states[0])
        assertTrue(states[1] is ExchangeDetailState.Error)
        assertEquals(errorMessage, (states[1] as ExchangeDetailState.Error).message)
        job.cancel()
    }

    @Test
    fun `loadAssets should emit Error state when use case throws exception`() = runTest {
        // Arrange
        val exchangeId = "binance"
        val exceptionMessage = "Unknown error"
        coEvery { mockGetExchangeDetailsUseCase(exchangeId) } returns flow {
            throw RuntimeException(exceptionMessage)
        }

        val states = mutableListOf<ExchangeDetailState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadAssets(exchangeId)
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeDetailState.Loading, states[0])
        assertTrue(states[1] is ExchangeDetailState.Error)
        assertEquals(exceptionMessage, (states[1] as ExchangeDetailState.Error).message)
        job.cancel()
    }

    @Test
    fun `loadAssets should pass correct exchangeId to use case`() = runTest {
        // Arrange
        val exchangeId = "custom-exchange-123"
        coEvery { mockGetExchangeDetailsUseCase(exchangeId) } returns flowOf(ApiResult.Success(emptyList()))

        val states = mutableListOf<ExchangeDetailState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadAssets(exchangeId)
        advanceUntilIdle()

        // Assert
        verify { mockGetExchangeDetailsUseCase(exchangeId) }
        job.cancel()
    }
}