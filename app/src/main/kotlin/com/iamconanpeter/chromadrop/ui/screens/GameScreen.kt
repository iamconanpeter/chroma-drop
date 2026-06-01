package com.iamconanpeter.chromadrop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.iamconanpeter.chromadrop.engine.model.GameSnapshot
import com.iamconanpeter.chromadrop.ui.components.BoardCanvas
import com.iamconanpeter.chromadrop.ui.components.HudBar

@Composable
fun GameScreen(
    snapshot: GameSnapshot,
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onRotate: () -> Unit,
    onSoftDrop: () -> Unit,
    onHardDrop: () -> Unit,
    onUndo: () -> Unit,
    onPauseResume: () -> Unit,
    onExit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D1420), Color(0xFF1B2B3F)),
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        HudBar(snapshot)
        BoardCanvas(snapshot = snapshot, modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = onMoveLeft, modifier = Modifier.weight(1f)) { Text("Left") }
            Button(onClick = onRotate, modifier = Modifier.weight(1f)) { Text("Rotate") }
            Button(onClick = onMoveRight, modifier = Modifier.weight(1f)) { Text("Right") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = onSoftDrop, modifier = Modifier.weight(1f)) { Text("Soft") }
            Button(onClick = onHardDrop, modifier = Modifier.weight(1f)) { Text("Hard") }
            Button(onClick = onUndo, modifier = Modifier.weight(1f)) { Text("Undo") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = onPauseResume, modifier = Modifier.weight(1f)) {
                Text(if (snapshot.isPaused) "Resume" else "Pause")
            }
            Button(onClick = onExit, modifier = Modifier.weight(1f)) { Text("Home") }
        }
    }
}
