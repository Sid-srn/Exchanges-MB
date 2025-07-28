package com.example.coinmarketcapexchanges.excharges.data.remote

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.core.network.CoinMarketCapApi
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeAssetsResponse
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse

class ExchangeRemoteDataSourceImpl (private val api: CoinMarketCapApi): ExchangeRemoteDataSource {

    override suspend fun getExchangeInfo(): ApiResult<ExchangeInfoResponse> {
        return try {
            val response = api.getExchangeInfo()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("Response body is null")
            } else {
                ApiResult.Error(response.message())
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getExchangeAssets(exchangeId: String): ApiResult<ExchangeAssetsResponse> {
        return try {
            val response = api.getExchangeAssets(id = exchangeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("Response body is null")
            } else {
                ApiResult.Error(response.message())
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}