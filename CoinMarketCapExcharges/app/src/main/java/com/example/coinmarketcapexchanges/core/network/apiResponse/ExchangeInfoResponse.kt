package com.example.coinmarketcapexchanges.core.network.apiResponse

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.squareup.moshi.Json

data class ExchangeInfoResponse (
    @Json(name = "status") val status: DataApiStatus,
    @Json(name = "data") val data: Map<String, ExchangeInfoDto>
)