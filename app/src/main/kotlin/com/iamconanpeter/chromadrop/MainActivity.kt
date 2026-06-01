package com.iamconanpeter.chromadrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.iamconanpeter.chromadrop.data.DataStoreProgressRepository
import com.iamconanpeter.chromadrop.ui.ChromaDropApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = DataStoreProgressRepository(applicationContext)

        setContent {
            MaterialTheme {
                ChromaDropApp(repository = repository)
            }
        }
    }
}
