package com.example.coinmarketcapexchanges.excharges.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coinmarketcapexchanges.TestDataItens
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangesUseCase
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeListState
import io.mockk.coEvery
import io.mockk.mockk
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
class ExchangeListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val mockGetExchangesUseCase: GetExchangesUseCase = mockk()
    private lateinit var viewModel: ExchangeListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Configuração padrão para o use case
        //val mockExchanges = TestDataItens.getExchangeInfo()
        //coEvery { mockGetExchangesUseCase() } returns flowOf(ApiResult.Success(mockExchanges))

        viewModel = ExchangeListViewModel(mockGetExchangesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Assert
        assertEquals(ExchangeListState.Loading, viewModel.state.value)
    }

    @Test
    fun `loadExchanges should emit Success state when use case returns data`() = runTest {
        // Arrange
        val mockExchanges = TestDataItens.getExchangeInfo()

        // Substitui a configuração padrão para este teste
        coEvery { mockGetExchangesUseCase() } returns flowOf(ApiResult.Success(mockExchanges))

        val states = mutableListOf<ExchangeListState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadExchanges()
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertTrue(states[0] is ExchangeListState.Loading)
        assertEquals(mockExchanges, (states[1] as ExchangeListState.Success).exchanges)
        job.cancel()
    }

    @Test
    fun `loadExchanges should emit Empty state when use case returns empty list`() = runTest {
        // Arrange
        coEvery { mockGetExchangesUseCase() } returns flowOf(ApiResult.Success(emptyList()))

        val states = mutableListOf<ExchangeListState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadExchanges()
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeListState.Loading, states[0])
        assertEquals(ExchangeListState.Empty, states[1])
        job.cancel()
    }

    @Test
    fun `loadExchanges should emit Error state when use case returns error`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        coEvery { mockGetExchangesUseCase() } returns flowOf(ApiResult.Error(errorMessage))

        val states = mutableListOf<ExchangeListState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadExchanges()
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeListState.Loading, states[0])
        assertTrue(states[1] is ExchangeListState.Error)
        assertEquals(errorMessage, (states[1] as ExchangeListState.Error).message)
        job.cancel()
    }

    @Test
    fun `loadExchanges should emit Error state when use case throws exception`() = runTest {
        // Arrange
        val exceptionMessage = "Unknown error"
        coEvery { mockGetExchangesUseCase() } returns flow {
            throw RuntimeException(exceptionMessage)
        }

        val states = mutableListOf<ExchangeListState>()
        val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect { states.add(it) }
        }

        // Act
        viewModel.loadExchanges()
        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(ExchangeListState.Loading, states[0])
        assertTrue(states[1] is ExchangeListState.Error)
        assertEquals(exceptionMessage, (states[1] as ExchangeListState.Error).message)
        job.cancel()
    }
}