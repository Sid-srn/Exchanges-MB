package com.example.coinmarketcapexchanges.excharges.data.remote

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeAssetsResponse
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse

interface ExchangeRemoteDataSource {
    suspend fun getExchangeInfo(): ApiResult<ExchangeInfoResponse>
    suspend fun getExchangeAssets(exchangeId: String): ApiResult<ExchangeAssetsResponse>
}