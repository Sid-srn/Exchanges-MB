package com.example.coinmarketcapexchanges.core.network.apiResponse

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.squareup.moshi.Json

data class ExchangeAssetsResponse(
    @Json(name = "status") val status: DataApiStatus,
    @Json(name = "data") val data: List<ExchangeAssetsDto>
)