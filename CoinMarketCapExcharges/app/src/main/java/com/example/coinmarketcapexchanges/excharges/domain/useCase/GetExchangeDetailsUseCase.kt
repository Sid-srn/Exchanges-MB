package com.example.coinmarketcapexchanges.excharges.domain.useCase

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExchangeDetailsUseCase (
    private val repository: ExchangeRepository
){
    operator fun invoke(exchangeId: String): Flow<ApiResult<List<ExchangeAssetsDto>>> = flow {
        try {
            val result = repository.getExchangeDetails(exchangeId)
            emit(result)
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}