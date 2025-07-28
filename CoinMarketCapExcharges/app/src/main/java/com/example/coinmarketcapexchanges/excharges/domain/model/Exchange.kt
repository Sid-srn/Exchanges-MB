package com.example.coinmarketcapexchanges.excharges.domain.model

data class Exchange(
    val id: String,
    val name: String,
    val slug: String,
    val logo: String,
    val description: String,
    val dateLaunched: String,
    val volumeUsd: Double
)