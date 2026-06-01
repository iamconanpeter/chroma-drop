package com.iamconanpeter.chromadrop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iamconanpeter.chromadrop.data.model.Settings

@Composable
fun SettingsScreen(
    settings: Settings,
    onSave: (Settings) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F1C22), Color(0xFF1E3845))))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ToggleRow(
            label = "Audio",
            checked = settings.audioEnabled,
            onToggle = { onSave(settings.copy(audioEnabled = it)) },
        )
        ToggleRow(
            label = "Haptics",
            checked = settings.hapticsEnabled,
            onToggle = { onSave(settings.copy(hapticsEnabled = it)) },
        )
        ToggleRow(
            label = "Colorblind Mode",
            checked = settings.colorblindMode,
            onToggle = { onSave(settings.copy(colorblindMode = it)) },
        )

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, color = Color.White)
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}
