package com.example.coinmarketcapexchanges.excharges.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangeDetailsUseCase
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeAssetsViewModel (
    private val getExchangeDetailsUseCase: GetExchangeDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExchangeDetailState>(ExchangeDetailState.Loading)
    val state: StateFlow<ExchangeDetailState> = _state

    fun loadAssets(exchangeId: String) {
        getExchangeDetailsUseCase(exchangeId).onEach { result ->
            when ( result) {
                is ApiResult.Success -> {
                    _state.value = ExchangeDetailState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _state.value = ExchangeDetailState.Error(result.message)
                }

            }
        }.catch  { e ->
            _state.value = ExchangeDetailState.Error(e.message ?: "Unknown error")
        }.launchIn(viewModelScope)

    }
}
