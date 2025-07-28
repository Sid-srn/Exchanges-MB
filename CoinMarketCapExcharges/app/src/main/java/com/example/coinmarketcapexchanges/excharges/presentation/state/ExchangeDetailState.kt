package com.example.coinmarketcapexchanges.excharges.presentation.state

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto

sealed class ExchangeDetailState {
    data object Loading : ExchangeDetailState()
    data class Success(val details: List<ExchangeAssetsDto>) : ExchangeDetailState()
    data class Error(val message: String) : ExchangeDetailState()
}