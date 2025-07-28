package com.example.coinmarketcapexchanges.core.network

import com.example.coinmarketcapexchanges.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("X-CMC_PRO_API_KEY",  BuildConfig.API_KEY)
                .header("Accept", "application/json")
            val request = requestBuilder.build()
            val requestLog = chain.request()
            Timber.d("Request URL: ${requestLog.url}")
            Timber.d("Headers: ${requestLog.headers}")
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/")
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
        .build()
}

fun provideCoinMarketCapApi(retrofit: Retrofit): CoinMarketCapApi {
    return retrofit.create(CoinMarketCapApi::class.java)
}