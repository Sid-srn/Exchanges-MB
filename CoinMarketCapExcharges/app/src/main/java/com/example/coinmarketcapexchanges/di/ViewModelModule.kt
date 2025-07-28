package com.example.coinmarketcapexchanges.di

import com.example.coinmarketcapexchanges.excharges.presentation.viewModel.ExchangeAssetsViewModel
import com.example.coinmarketcapexchanges.excharges.presentation.viewModel.ExchangeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ExchangeListViewModel(get()) }
    viewModel { ExchangeAssetsViewModel(get()) }
}
