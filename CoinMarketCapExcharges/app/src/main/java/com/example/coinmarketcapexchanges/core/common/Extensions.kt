package com.example.coinmarketcapexchanges.core.common

import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.domain.model.Exchange

import java.text.SimpleDateFormat
import java.util.Locale

fun ExchangeInfoDto.toExchange(): Exchange {
    return Exchange(
        id = id,
        name = name,
        slug = slug,
        logo = logo ?: "",
        description = description ?: "",
        dateLaunched = dateLaunched ?: "",
        volumeUsd = spotVolumeUsd ?: 0.0
    )
}

fun Double.formattedVolume(): String {
    return when {
        this >= 1_000_000_000 -> "%.2fB".format(this / 1_000_000_000)
        this >= 1_000_000 -> "%.2fM".format(this / 1_000_000)
        this >= 1_000 -> "%.2fK".format(this / 1_000)
        else -> "%.2f".format(this)
    }
}

fun String.formatToYear(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        this.take(4) // Pega apenas o ano se a formatação falhar
    }
}