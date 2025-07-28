package com.example.coinmarketcapexchanges.excharges.presentation.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coinmarketcapexchanges.ui.theme.Pink40
import com.example.coinmarketcapexchanges.ui.theme.Pink80
import com.example.coinmarketcapexchanges.ui.theme.Purple40
import com.example.coinmarketcapexchanges.ui.theme.Purple80
import com.example.coinmarketcapexchanges.ui.theme.PurpleGrey40
import com.example.coinmarketcapexchanges.ui.theme.PurpleGrey80

@Composable
fun YourAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80
        )
    } else {
        lightColorScheme(
            primary = Purple40,
            secondary = PurpleGrey40,
            tertiary = Pink40
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            titleMedium = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp
            )
        ),
        shapes = Shapes(
            medium = RoundedCornerShape(12.dp)
        ),
        content = content
    )
}