package com.example.coinmarketcapexchanges.di

import com.example.coinmarketcapexchanges.core.network.provideCoinMarketCapApi
import com.example.coinmarketcapexchanges.core.network.provideOkHttpClient
import com.example.coinmarketcapexchanges.core.network.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideCoinMarketCapApi(get()) }
}

