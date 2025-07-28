package com.example.coinmarketcapexchanges.excharges.presentation.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.coinmarketcapexchanges.R
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeAssetsDto
import com.example.coinmarketcapexchanges.excharges.data.remote.dto.ExchangeInfoDto
import com.example.coinmarketcapexchanges.excharges.presentation.component.FullScreenLoading
import com.example.coinmarketcapexchanges.excharges.presentation.state.ExchangeDetailState
import com.example.coinmarketcapexchanges.excharges.presentation.viewModel.ExchangeAssetsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeDetailScreen(
    assetsViewModel: ExchangeAssetsViewModel = koinViewModel(),
    navController: NavController
) {

    val assetsState by assetsViewModel.state.collectAsState()
    val exchangeState = remember {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<ExchangeInfoDto?>("selected_exchange", null)
    }?.collectAsState()

    val exchange = exchangeState?.value

    if (exchange == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhuma exchange selecionada")
        }
        return
    } else {
        assetsViewModel.loadAssets(exchange.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exchange.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        when (val currentState = assetsState) {
            is ExchangeDetailState.Loading -> FullScreenLoading()
            is ExchangeDetailState.Success -> ExchangeDetailContent(
                padding,
                exchange,
                currentState
            )
            is ExchangeDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorMessage(message = currentState.message)
                }
            }
        }
    }
}

@Composable
private fun ExchangeDetailContent(
    padding: PaddingValues,
    exchange: ExchangeInfoDto?,
    currentState: ExchangeDetailState.Success
) {
    LazyColumn(modifier = Modifier.padding(padding)) {
        // Seção de informações da exchange
        item {
            ExchangeInfoSection(exchange)
        }
        // Lista de ativos
        item {
            Text(
                text = "Ativos Disponíveis",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (currentState.details.isEmpty()) {
            item {
                Text(
                    "Nenhum ativo disponível",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            items(currentState.details) { asset ->
                AssetListItem(asset)
                Divider()
            }
        }
    }
}

@Composable
private fun ExchangeInfoSection(exchange: ExchangeInfoDto?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header com logo e nome
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = exchange?.logo ?: "",
                    contentDescription = "Logo ${exchange?.name}",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(R.drawable.ic_exchange_placeholder),
                    error = painterResource(R.drawable.ic_exchange_placeholder)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = exchange?.name ?: "",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "ID: ${exchange?.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição
            Text(
                text = exchange?.description?.replace("#", "")?.trim()
                    ?: "Descrição não disponível",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Grid de informações
            GridInfoSection(exchange)
        }
    }
}

@Composable
private fun GridInfoSection(exchange: ExchangeInfoDto?) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Linha 1 - Website e Data de Lançamento
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Website
            exchange?.urls?.website?.firstOrNull()?.let { website ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Website",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    ClickableText(
                        text = buildAnnotatedString {
                            append(website.removePrefix("https://").removePrefix("http://"))
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ),
                        onClick = {
                            try {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(website)
                                    )
                                )
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Não foi possível abrir o site",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Data de lançamento
            exchange?.dateLaunched?.let { date ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Fundação",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(formatLaunchDate(date))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Linha 2 - Taxas Maker e Taker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Taxa Maker",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text("${exchange?.makerFee?.times(100) ?: 0.0}%")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Taxa Taker",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text("${exchange?.takerFee?.times(100) ?: 0.0}%")
            }
        }
    }
}

@Composable
private fun AssetListItem(asset: ExchangeAssetsDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = asset.currency.name,
            style = MaterialTheme.typography.bodyLarge
        )

        asset.currency.priceUsd.let { price ->
            Text(
                text = "$${"%.4f".format(price)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

private fun formatLaunchDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        dateString
    }
}