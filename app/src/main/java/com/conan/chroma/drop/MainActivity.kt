package com.conan.chroma.drop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.conan.chroma.drop.ui.theme.ChromaDropTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChromaDropTheme {
                Surface(color = MaterialTheme.colors.background) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    Text("Chroma Drop MVP – Coming Soon!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChromaDropTheme {
        GameScreen()
    }
}
