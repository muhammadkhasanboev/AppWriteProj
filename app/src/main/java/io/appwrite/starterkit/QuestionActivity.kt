package io.appwrite.starterkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import io.appwrite.starterkit.data.models.QuizResponse

class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Get QuizResponse (you can use a static singleton now, until you add Parcelable support)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                }
            }
        }
    }
}


