package com.example.coinmarketcapexchanges.excharges.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ExchangeAssetsDto(
    @Json(name = "wallet_address") val walletAddress: String,
    @Json(name = "balance") val balance: Double,
    @Json(name = "platform") val platform: CryptoPlatform,
    @Json(name = "currency") val currency: CryptoCurrency
)

@JsonClass(generateAdapter = true)
data class CryptoPlatform(
    @Json(name = "crypto_id") val cryptoId: Long,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class CryptoCurrency(
    @Json(name = "crypto_id") val cryptoId: Long,
    @Json(name = "price_usd") val priceUsd: Double,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String
)