package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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





class MainActivity : ComponentActivity() {
    val MyCustomColor = Color(0xFF8EA17A)

    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Appwrite client
        val client = Client(this)
            .setEndpoint("https://fra.cloud.appwrite.io/v1")
            .setProject("686f662d00384d0a13b9")

        account = Account(client)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1CBB6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

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
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    account.deleteSession("current")
                    onLogout()
                } catch (e: AppwriteException) {
                    // Optionally handle error
                }
            }
        }) {
            Text("Logout")
        }
    }
}

// Generic Dropdown Composable
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
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(Color(0xFF8EA17A))
            .clickable { expanded = true }
            .padding(12.dp)
    ) {
        Text(text = selected?.name ?: label, color=Color.White)

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
