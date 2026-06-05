package com.example.chromadrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChromaDropApp()
        }
    }
}

@Composable
fun ChromaDropApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101020))
    ) {
        // TODO: Game UI goes here
    }
}
