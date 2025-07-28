package com.example.coinmarketcapexchanges.excharges.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coinmarketcapexchanges.R
import com.example.coinmarketcapexchanges.core.common.formatToYear
import com.example.coinmarketcapexchanges.core.common.formattedVolume
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.presentation.component.EmptyListView
import com.example.coinmarketcapexchanges.excharges.presentation.component.ErrorRetryView
import com.example.coinmarketcapexchanges.excharges.presentation.component.FullScreenLoading
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeListState
import com.example.coinmarketcapexchanges.excharges.presentation.viewModel.ExchangeListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeListScreen(
    viewModel: ExchangeListViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crypto Exchanges") }
            )
        }
    ) { paddingValues ->
        when (val currentState = state) {
            is ExchangeListState.Loading -> FullScreenLoading()
            is ExchangeListState.Success -> ExchangeListContent(
                exchanges = currentState.exchanges,
                onItemClick = { exchange ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_exchange", exchange)
                    navController.navigate("exchange_detail"){
                        launchSingleTop=true
                    }
                },
                modifier = Modifier.padding(paddingValues)
            )
            is ExchangeListState.Error -> ErrorRetryView(
                message = currentState.message,
                onRetry = { viewModel.loadExchanges() },
                modifier = Modifier.padding(paddingValues)
            )
            is ExchangeListState.Empty -> EmptyListView(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun ExchangeListContent(
    exchanges: List<ExchangeInfoDto>,
    onItemClick: (ExchangeInfoDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(exchanges) { exchange ->
            ExchangeListItem(
                exchange = exchange,
                onItemClick = { onItemClick(exchange) }
            )
        }
    }
}

@Composable
fun ExchangeListItem(
    exchange: ExchangeInfoDto,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone/Logo da Exchange
            AsyncImage(
                model = exchange.logo,
                contentDescription = "${exchange.name} logo",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.ic_exchange_placeholder),
                error = painterResource(R.drawable.ic_exchange_placeholder)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informações principais
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exchange.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    // Volume Spot
                    exchange.spotVolumeUsd?.let { volume ->
                        Text(
                            text = "Volume: $${volume.formattedVolume()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Data de lançamento
                    exchange.dateLaunched?.let { date ->
                        Text(
                            text = "Desde: ${date.formatToYear()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }


        }
    }
}