package io.appwrite.starterkit.io.appwrite.starterkit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.appwrite.starterkit.io.appwrite.starterkit.ui.DifficultySelector
import io.appwrite.starterkit.io.appwrite.starterkit.ui.NumberOfQuestionsSelector
import io.appwrite.starterkit.io.appwrite.starterkit.ui.QuizTypeSelector
import kotlinx.serialization.Serializable

@Serializable
object CustomizeQuizScreen


@Composable
@Preview(showSystemUi = true)
fun CustomizeQuizScreen() {
    var selectedDifficulty by remember { mutableStateOf("Easy") }
    var selectedType by remember { mutableStateOf("Multiple Choice") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Difficulty", style = MaterialTheme.typography.titleMedium, )
        DifficultySelector(selectedDifficulty) { selectedDifficulty = it }
        Spacer(modifier = Modifier.height(30.dp))

        Text("Select Quiz Type", style = MaterialTheme.typography.titleMedium)
        QuizTypeSelector(selectedType) { selectedType = it }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Select number of questions", style = MaterialTheme.typography.titleMedium)
        NumberOfQuestionsSelector()

        Spacer(modifier = Modifier.height(40.dp))

        Button(modifier=Modifier,
                colors = ButtonDefaults.buttonColors(
                     containerColor = Color(0xFFF5BA69),
                     contentColor = Color(0xFF0D0D0C)),
                onClick = {
            // Proceed with selected values
        }) {
            Text("Start Quiz")
        }
    }
}

