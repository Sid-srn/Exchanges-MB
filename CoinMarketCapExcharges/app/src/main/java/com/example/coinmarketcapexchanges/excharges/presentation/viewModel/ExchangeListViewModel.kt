package com.example.coinmarketcapexchanges.excharges.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangesUseCase
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeListViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExchangeListState>(ExchangeListState.Loading)
    val state: StateFlow<ExchangeListState> = _state

    fun loadExchanges() {
        getExchangesUseCase().onEach { result ->
            when ( result) {
                is ApiResult.Success -> {
                    if (result.data.isEmpty()) {
                        _state.value = ExchangeListState.Empty
                    } else {
                        _state.value = ExchangeListState.Success(result.data)
                    }
                }

                is ApiResult.Error -> {
                    _state.value = ExchangeListState.Error(result.message)
                }

            }
        }.catch  { e ->
            _state.value = ExchangeListState.Error(e.message ?: "Unknown error")
        }.launchIn(viewModelScope)

    }
}
