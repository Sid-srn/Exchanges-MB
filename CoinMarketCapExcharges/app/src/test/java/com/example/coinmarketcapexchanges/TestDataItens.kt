package com.example.coinmarketcapexchanges

import com.example.coinmarketcapexchanges.core.network.apiResponse.DataApiStatus
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeAssetsResponse
import com.example.coinmarketcapexchanges.core.network.apiResponse.ExchangeInfoResponse
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.CryptoCurrency
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.CryptoPlatform
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeUrls

object TestDataItens {
    fun getInfoResponse() = ExchangeInfoResponse(
        status = DataApiStatus(
            timestamp = "2025-07-27T17:14:56.352Z",
            errorCode = 0,
            errorMessage = null
        ),
        data = mapOf(
            "270" to ExchangeInfoDto(
                id = "270",
                name = "Binance",
                slug = "binance",
                logo = "https://s2.coinmarketcap.com/static/img/exchanges/64x64/270.png",
                description = "## What Is Binance?\n\nBinance is the world's largest cryptocurrency exchange...", // (texto completo omitido por brevidade)
                dateLaunched = "2017-07-14T00:00:00.000Z",
                spotVolumeUsd = 15297129675.795628,
                makerFee = 0.02,
                takerFee = 0.04,
                urls = ExchangeUrls(
                    website = listOf("https://www.binance.com/")
                )
            )
        )
    )

    fun getExchangeAssetResponse() = ExchangeAssetsResponse(
        status = DataApiStatus(
            timestamp = "2025-07-28T21:58:55.195Z",
            errorCode = 0,
            errorMessage = null
        ),
        data = listOf(
            ExchangeAssetsDto(
                walletAddress = "0xf977814e90da44bfa03b6295a0616a897441acec",
                balance = 466373270.52,
                platform = CryptoPlatform(
                    cryptoId = 11841,
                    symbol = "ARB",
                    name = "Arbitrum"
                ),
                currency = CryptoCurrency(
                    cryptoId = 11841,
                    priceUsd = 0.43410326647127323,
                    symbol = "ARB",
                    name = "Arbitrum"
                )
            ),
            ExchangeAssetsDto(
                walletAddress = "0x5a52e96bacdabb82fd05763e25335261b270efcb",
                balance = 53310792.83,
                platform = CryptoPlatform(
                    cryptoId = 1027,
                    symbol = "ETH",
                    name = "Ethereum"
                ),
                currency = CryptoCurrency(
                    cryptoId = 4758,
                    priceUsd = 0.030753171513774942,
                    symbol = "DF",
                    name = "dForce"
                )
            ),
            ExchangeAssetsDto(
                walletAddress = "0xf977814e90da44bfa03b6295a0616a897441acec",
                balance = 128385344.87,
                platform = CryptoPlatform(
                    cryptoId = 1027,
                    symbol = "ETH",
                    name = "Ethereum"
                ),
                currency = CryptoCurrency(
                    cryptoId = 9543,
                    priceUsd = 0.11586050180291885,
                    symbol = "BICO",
                    name = "Biconomy"
                )
            )
        )
    )

    fun getExchangeAssetsList() = listOf(
        ExchangeAssetsDto(
            walletAddress = "0xf977814e90da44bfa03b6295a0616a897441acec",
            balance = 466373270.52,
            platform = CryptoPlatform(
                cryptoId = 11841,
                symbol = "ARB",
                name = "Arbitrum"
            ),
            currency = CryptoCurrency(
                cryptoId = 11841,
                priceUsd = 0.43410326647127323,
                symbol = "ARB",
                name = "Arbitrum"
            )
        ),
        ExchangeAssetsDto(
            walletAddress = "0x5a52e96bacdabb82fd05763e25335261b270efcb",
            balance = 53310792.83,
            platform = CryptoPlatform(
                cryptoId = 1027,
                symbol = "ETH",
                name = "Ethereum"
            ),
            currency = CryptoCurrency(
                cryptoId = 4758,
                priceUsd = 0.030753171513774942,
                symbol = "DF",
                name = "dForce"
            )
        ),
        ExchangeAssetsDto(
            walletAddress = "0xf977814e90da44bfa03b6295a0616a897441acec",
            balance = 128385344.87,
            platform = CryptoPlatform(
                cryptoId = 1027,
                symbol = "ETH",
                name = "Ethereum"
            ),
            currency = CryptoCurrency(
                cryptoId = 9543,
                priceUsd = 0.11586050180291885,
                symbol = "BICO",
                name = "Biconomy"
            )
        )
    )

    fun getExchangeInfo() = listOf(
        ExchangeInfoDto(
            id = "270",
            name = "Binance",
            slug = "binance",
            logo = "https://s2.coinmarketcap.com/static/img/exchanges/64x64/270.png",
            description = "## What Is Binance?\n\nBinance is the world's largest cryptocurrency exchange...", // (texto completo omitido por brevidade)
            dateLaunched = "2017-07-14T00:00:00.000Z",
            spotVolumeUsd = 15297129675.795628,
            makerFee = 0.02,
            takerFee = 0.04,
            urls = ExchangeUrls(
                website = listOf("https://www.binance.com/")
            )
        )
    )


    fun getEmptyExchangeAssetResponse() = ExchangeAssetsResponse(
        status = DataApiStatus(
            timestamp = "2025-07-28T21:58:55.195Z",
            errorCode = 0,
            errorMessage = null
        ),
        data = listOf()

    )
}