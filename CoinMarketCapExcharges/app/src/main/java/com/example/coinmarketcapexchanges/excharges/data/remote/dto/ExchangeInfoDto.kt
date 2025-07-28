package com.example.coinmarketcapexchanges.excharges.data.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ExchangeInfoDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "logo") val logo: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "date_launched") val dateLaunched: String?,
    @Json(name = "spot_volume_usd") val spotVolumeUsd: Double?,
    @Json(name = "maker_fee") val makerFee: Double?,
    @Json(name = "taker_fee") val takerFee: Double?,
    @Json(name = "urls") val urls: ExchangeUrls?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ExchangeUrls(
    @Json(name = "website") val website: List<String>?
) : Parcelable