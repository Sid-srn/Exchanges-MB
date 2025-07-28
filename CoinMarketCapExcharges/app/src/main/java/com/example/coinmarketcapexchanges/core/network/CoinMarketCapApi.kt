package com.example.coinmarketcapexchanges.core.network

import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeAssetsResponse
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApi {
    @GET("v1/exchange/info")
    suspend fun getExchangeInfo(
        @Query("id") id: String? = "270,89,24",
        @Query("slug") slug: String? = null,
        @Query("aux") aux: String? = null
    ): Response<ExchangeInfoResponse>

    @GET("v1/exchange/assets")
    suspend fun getExchangeAssets(
        @Query("id") id: String,
        @Query("aux") aux: String? = null
    ): Response<ExchangeAssetsResponse>
}