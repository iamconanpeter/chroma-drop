package com.example.chromadrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chroma Drop") },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { innerPadding ->
        GamePlaceholder(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun GamePlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Game will launch here",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
