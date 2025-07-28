package com.example.coinmarketcapexchanges.excharges.data.repository

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.ExchangeRemoteDataSource
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto

class ExchangeRepositoryImpl(
    private val remoteDataSource: ExchangeRemoteDataSource
) : ExchangeRepository {

    override suspend fun getExchanges(): ApiResult<List<ExchangeInfoDto>> {
        return when (val result = remoteDataSource.getExchangeInfo()) {
            is ApiResult.Success -> {
                val exchanges = result.data.data.values.map { it }
                ApiResult.Success(exchanges)
            }
            is ApiResult.Error -> result
        }
    }

    override suspend fun getExchangeDetails(exchangeId: String): ApiResult<List<ExchangeAssetsDto>> {
        return when (val result = remoteDataSource.getExchangeAssets(exchangeId)) {
            is ApiResult.Success -> {
                ApiResult.Success(result.data.data)
            }
            is ApiResult.Error -> result
        }
    }
}