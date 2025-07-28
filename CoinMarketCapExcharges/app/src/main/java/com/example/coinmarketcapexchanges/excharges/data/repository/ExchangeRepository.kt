package com.example.coinmarketcapexchanges.excharges.data.repository

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto

interface ExchangeRepository {
    suspend fun getExchanges(): ApiResult<List<ExchangeInfoDto>>
    suspend fun getExchangeDetails(exchangeId: String): ApiResult<List<ExchangeAssetsDto>>
}