package com.example.coinmarketcapexchanges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinmarketcapexchanges.excharges.presentation.component.AppNavigation
import com.example.coinmarketcapexchanges.excharges.presentation.component.YourAppTheme
import com.example.coinmarketcapexchanges.excharges.presentation.screen.ExchangeListScreen
import com.example.coinmarketcapexchanges.excharges.presentation.viewModel.ExchangeListViewModel
import com.example.coinmarketcapexchanges.ui.theme.CoinMarketCapExchargesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourAppTheme {
                AppNavigation()
                /*val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "exchange_list"
                ) {
                    composable("exchange_list") {
                        ExchangeListScreen(
                            onItemClick = { exchangeId ->
                                navController.navigate("exchange_details/$exchangeId")
                            }
                        )
                    }
                    composable("exchange_details/{exchangeId}") { backStackEntry ->
                        val exchangeId = backStackEntry.arguments?.getString("exchangeId") ?: ""
                        ExchangeDetailScreen()
                    }
                }*/
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoinMarketCapExchargesTheme {
        Greeting("Android")
    }
}