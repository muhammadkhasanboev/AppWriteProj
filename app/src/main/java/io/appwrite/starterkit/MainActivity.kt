package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.starterkit.data.models.QuizResponse
import io.appwrite.starterkit.viewmodels.QuizViewModel
import io.appwrite.starterkit.RetrofitInstance
//import io.appwrite.starterkit.ui.data.models.QuizScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = Client(this)
            .setEndpoint("https://fra.cloud.appwrite.io/v1")
            .setProject("686f662d00384d0a13b9")

        val account = Account(client)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(account = account) {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(account: Account, onLogout: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: QuizViewModel = viewModel()

    val categories = listOf(
        DropdownItem(9, "General Knowledge"),
        DropdownItem(10, "Books"),
        DropdownItem(11, "Film"),
        DropdownItem(12, "Music")
    )

    val difficulties = listOf(
        DropdownItem(1, "Easy"),
        DropdownItem(2, "Medium"),
        DropdownItem(3, "Hard")
    )

    val quiztype = listOf(
        DropdownItem(1, "Multiple Questions"),
        DropdownItem(2, "True/False")
    )

    val questionCounts = listOf(
        DropdownItem(5, "5 Questions"),
        DropdownItem(10, "10 Questions"),
        DropdownItem(15, "15 Questions"),
        DropdownItem(20, "20 Questions")
    )

    var selectedCategory by remember { mutableStateOf<DropdownItem?>(null) }
    var selectedDifficulty by remember { mutableStateOf<DropdownItem?>(null) }
    var selectedQuizType by remember { mutableStateOf<DropdownItem?>(null) }
    var selectedQuestionCount by remember { mutableStateOf<DropdownItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCADCFC))
    ) {
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        account.deleteSession("current")
                        onLogout()
                    } catch (_: AppwriteException) {
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00246B),
                contentColor = Color.White
            )
        ) {
            Text("Logout")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryDropdown("Choose Category", categories, selectedCategory) { selectedCategory = it }
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown("Choose Difficulty", difficulties, selectedDifficulty) { selectedDifficulty = it }
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown("Choose Quiz Type", quiztype, selectedQuizType) { selectedQuizType = it }
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown("Number of Questions", questionCounts, selectedQuestionCount) { selectedQuestionCount = it }
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                coroutineScope.launch {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.getQuizQuestions(
                            amount = selectedQuestionCount?.id ?: 10,
                            category = selectedCategory?.id,
                            difficulty = selectedDifficulty?.name?.lowercase(),
                            type = when (selectedQuizType?.name) {
                                "True/False" -> "boolean"
                                "Multiple Questions" -> "multiple"
                                else -> null
                            }
                        )
                    }
                    if (response.isSuccessful) {
                        val quizResponse: QuizResponse? = response.body()
                        viewModel.setQuiz(quizResponse)
                        // TODO: Navigate to QuestionActivity
                    } else {
                        println("API error: ${response.code()} - ${response.message()}")
                    }
                }
            }) {
                Text("Start Quiz")
            }
        }
    }
}

@Composable
fun CategoryDropdown(
    label: String,
    categories: List<DropdownItem>,
    selected: DropdownItem?,
    onSelected: (DropdownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .background(Color(0xFF649DF5), shape = RoundedCornerShape(30.dp))
            .clickable { expanded = true }
            .padding(horizontal = 12.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selected?.name ?: label, color = Color.White)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onSelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
