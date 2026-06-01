package com.iamconanpeter.chromadrop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iamconanpeter.chromadrop.data.model.PlayerStats

@Composable
fun HomeScreen(
    stats: PlayerStats,
    onStartEndless: () -> Unit,
    onStartDaily: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenDailySummary: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B1320), Color(0xFF12263A)),
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
    ) {
        Text("Chroma Drop", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Best Score: ${stats.bestScore}", color = Color.White)

        Button(onClick = onStartEndless, modifier = Modifier.fillMaxWidth()) {
            Text("Start Endless")
        }
        Button(onClick = onStartDaily, modifier = Modifier.fillMaxWidth()) {
            Text("Play Daily")
        }
        Button(onClick = onOpenDailySummary, modifier = Modifier.fillMaxWidth()) {
            Text("Daily Summary")
        }
        Button(onClick = onOpenSettings, modifier = Modifier.fillMaxWidth()) {
            Text("Settings")
        }
    }
}
