package com.example.coinmarketcapexchanges.core.network.apiResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataApiStatus(
    @Json(name = "timestamp") val timestamp: String,
    @Json(name = "error_code") val errorCode: Int?,
    @Json(name = "error_message") val errorMessage: String?
)