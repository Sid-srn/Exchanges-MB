package com.example.coinmarketcapexchanges.excharges.presentation.component

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinmarketcapexchanges.excharges.presentation.screen.ExchangeDetailScreen
import com.example.coinmarketcapexchanges.excharges.presentation.screen.ExchangeListScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "exchange_list"
    ) {
        composable("exchange_list") {
            ExchangeListScreen(
                navController = navController
            )
        }
        composable(
            route = "exchange_detail",
        ) {
            ExchangeDetailScreen(navController = navController)
        }
    }
}