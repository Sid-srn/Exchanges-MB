package com.example.coinmarketcapexchanges.excharges.domain.useCase

import com.example.coinmarketcapexchanges.core.common.ApiResult
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExchangesUseCase (
    private val repository: ExchangeRepository
){
    operator fun invoke(): Flow<ApiResult<List<ExchangeInfoDto>>> = flow {
        try {
            val result = repository.getExchanges()
            emit(result)
        } catch (e: Exception) {
            emit(ApiResult.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}