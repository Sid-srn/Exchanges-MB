package com.example.coinmarketcapexchanges.excharges.domain.model

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.CryptoCurrency
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.CryptoPlatform

data class ExchangeDetail(
    val walletAddress: String,
    val balance: Double,
    val platform: CryptoPlatform,
    val currency: CryptoCurrency
    )