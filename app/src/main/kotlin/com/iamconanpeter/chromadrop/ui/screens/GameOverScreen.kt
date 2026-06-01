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

@Composable
fun GameOverScreen(
    score: Int,
    onRetry: () -> Unit,
    onHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E0F16), Color(0xFF301C2C))))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
    ) {
        Text("Run Over", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Score: $score", color = Color.White)

        Button(onClick = onRetry, modifier = Modifier.fillMaxWidth()) { Text("Retry") }
        Button(onClick = onHome, modifier = Modifier.fillMaxWidth()) { Text("Back To Home") }
    }
}
