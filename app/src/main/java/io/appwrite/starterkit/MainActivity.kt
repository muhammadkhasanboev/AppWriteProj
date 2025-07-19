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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.starterkit.data.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import io.appwrite.starterkit.data.models.QuizResponse
import io.appwrite.starterkit.RetrofitInstance
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Appwrite client
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

    val categories = listOf(
        Category(1, "Science"),
        Category(2, "History"),
        Category(3, "Technology"),
        Category(4, "Politics"),
        Category(5, "Celebrities")
    )
    val difficulties = listOf(
        Category(1, "Easy"),
        Category(2, "Medium"),
        Category(3, "Hard")
    )
    val quiztype = listOf(
        Category(1, "Multiple Questions"),
        Category(2, "True/False")
    )
    val questionCounts = listOf(
        Category(5, "5 Questions"),
        Category(10, "10 Questions"),
        Category(15, "15 Questions"),
        Category(20, "20 Questions")
    )

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedDifficulty by remember { mutableStateOf<Category?>(null) }
    var selectedQuizType by remember { mutableStateOf<Category?>(null) }
    var selectedQuestionCount by remember { mutableStateOf<Category?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCADCFC))
    ) {
        // Logout Button at Top End
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        account.deleteSession("current")
                        onLogout()
                    } catch (e: AppwriteException) {
                        // Optionally handle error
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00246B), // dark blue background
                contentColor = Color.White // white text
            )
        ) {
            Text("Logout")
        }

        // Main Content (Spinners)
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryDropdown(
                label = "Choose Category",
                categories = categories,
                selected = selectedCategory,
                onSelected = { selectedCategory = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown(
                label = "Choose Difficulty",
                categories = difficulties,
                selected = selectedDifficulty,
                onSelected = { selectedDifficulty = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown(
                label = "Choose Quiz Type",
                categories = quiztype,
                selected = selectedQuizType,
                onSelected = { selectedQuizType = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown(
                label = "Number of Questions",
                categories = questionCounts,
                selected = selectedQuestionCount,
                onSelected = { selectedQuestionCount = it }
            )
            //////////////////////////////////////////////////////////////////////
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
                        // TODO: navigate to QuestionActivity and pass quizResponse or store it somewhere

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
    categories: List<Category>,
    selected: Category?,
    onSelected: (Category) -> Unit
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
