package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.appwrite.starterkit.data.models.QuizResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizResponse(
    val response_code: Int,
    val results: List<Question>
) : android.os.Parcelable

@Parcelize
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
) : android.os.Parcelable

class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quizResponse = intent.getParcelableExtra<QuizResponse>("QUIZ_RESPONSE")

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    quizResponse?.let {
                        QuestionScreen(quizResponse = it)
                    } ?: Text("No quiz data available")
                }
            }
        }
    }
}

@Composable
fun QuestionScreen(quizResponse: QuizResponse) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var quizFinished by remember { mutableStateOf(false) }

    val currentQuestion = quizResponse.results.getOrNull(currentQuestionIndex)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCADCFC))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        if (!quizFinished && currentQuestion != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Question ${currentQuestionIndex + 1}/${quizResponse.results.size}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))

                val answers = (currentQuestion.incorrect_answers + currentQuestion.correct_answer).shuffled()
                answers.forEach { answer ->
                    Button(
                        onClick = {
                            selectedAnswer = answer
                            showResult = true
                            if (answer == currentQuestion.correct_answer) {
                                score++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(vertical = 4.dp),
                        enabled = !showResult,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                selectedAnswer == answer && answer == currentQuestion.correct_answer -> Color.Green
                                selectedAnswer == answer -> Color.Red
                                else -> Color(0xFF649DF5)
                            },
                            contentColor = Color.White
                        )
                    ) {
                        Text(answer)
                    }
                }

                if (showResult) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (selectedAnswer == currentQuestion.correct_answer) {
                            "Correct!"
                        } else {
                            "Incorrect. Correct answer: ${currentQuestion.correct_answer}"
                        },
                        color = if (selectedAnswer == currentQuestion.correct_answer) Color.Green else Color.Red
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (currentQuestionIndex < quizResponse.results.size - 1) {
                                currentQuestionIndex++
                                selectedAnswer = null
                                showResult = false
                            } else {
                                quizFinished = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00246B),
                            contentColor = Color.White
                        )
                    ) {
                        Text(if (currentQuestionIndex < quizResponse.results.size - 1) "Next Question" else "Finish Quiz")
                    }
                }
            }
        } else {
            // Quiz finished
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Quiz Completed!\nScore: $score / ${quizResponse.results.size}",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF00246B)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        // Reset quiz
                        currentQuestionIndex = 0
                        score = 0
                        selectedAnswer = null
                        showResult = false
                        quizFinished = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF649DF5),
                        contentColor = Color.White
                    )
                ) {
                    Text("Restart Quiz")
                }

                Button(
                    onClick = {
                        // Navigate to MainActivity
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00246B),
                        contentColor = Color.White
                    )
                ) {
                    Text("Another Quiz")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
