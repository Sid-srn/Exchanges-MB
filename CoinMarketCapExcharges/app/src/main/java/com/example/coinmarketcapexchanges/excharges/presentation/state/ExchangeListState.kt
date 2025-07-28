package com.example.coinmarketcapexchanges.excharges.presentation.state

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto

sealed class ExchangeListState {
    data object Loading : ExchangeListState()
    data class Success(val exchanges: List<ExchangeInfoDto>) : ExchangeListState()
    data class Error(val message: String) : ExchangeListState()
    data object Empty : ExchangeListState()
}