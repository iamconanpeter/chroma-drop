package com.iamconanpeter.chromadrop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.iamconanpeter.chromadrop.engine.model.GameSnapshot

@Composable
fun HudBar(snapshot: GameSnapshot) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF162434), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text("Score ${snapshot.score}", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Combo ${snapshot.combo}", color = Color.White)
        Text("Undo ${if (snapshot.undoAvailable) "Ready" else "Used"}", color = Color.White)
    }
}
