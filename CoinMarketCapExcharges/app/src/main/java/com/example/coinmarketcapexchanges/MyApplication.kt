package com.example.coinmarketcapexchanges

import android.app.Application
import com.example.coinmarketcapexchanges.di.appModule
import com.example.coinmarketcapexchanges.di.networkModule
import com.example.coinmarketcapexchanges.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}