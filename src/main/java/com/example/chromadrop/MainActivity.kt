package com.example.chromadrop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Simple Compose placeholder
        val composeView = ComposeView(this).apply {
            setContent {
                // TODO: Compose UI
            }
        }
        setContentView(composeView)
    }
}
