package com.example.coinmarketcapexchanges.di

import com.example.coinmarketcapexchanges.excharges.data.remote.ExchangeRemoteDataSource
import com.example.coinmarketcapexchanges.excharges.data.remote.ExchangeRemoteDataSourceImpl
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepository
import com.example.coinmarketcapexchanges.excharges.data.repository.ExchangeRepositoryImpl
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangeDetailsUseCase
import com.example.coinmarketcapexchanges.excharges.domain.useCase.GetExchangesUseCase
import org.koin.dsl.module

val appModule = module {
    single<ExchangeRemoteDataSource> { ExchangeRemoteDataSourceImpl(get()) }
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    factory { GetExchangesUseCase(get()) }
    factory { GetExchangeDetailsUseCase(get()) }
}