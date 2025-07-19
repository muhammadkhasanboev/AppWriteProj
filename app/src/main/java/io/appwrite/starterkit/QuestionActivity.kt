package io.appwrite.starterkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import io.appwrite.starterkit.viewmodels.QuizViewModel

class QuestionActivity : ComponentActivity() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👇 Connect your ViewModel
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[QuizViewModel::class.java]

        setContent {
            // your Jetpack Compose quiz screen...
        }

        // 🔥 Example call (replace with real values)
        val score = 7
        val username = "muhammad42"
        viewModel.storeScore(username, score)
    }
}



