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
import com.iamconanpeter.chromadrop.data.model.DailyRecord

@Composable
fun DailySummaryScreen(
    record: DailyRecord?,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF13202D), Color(0xFF223E57))))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
    ) {
        Text("Daily Summary", color = Color.White, fontWeight = FontWeight.Bold)

        if (record == null) {
            Text("No daily result yet", color = Color.White)
        } else {
            Text("Date: ${record.dateKey}", color = Color.White)
            Text("Best Daily Score: ${record.score}", color = Color.White)
            Text("Stars: ${record.stars}", color = Color.White)
        }

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}
