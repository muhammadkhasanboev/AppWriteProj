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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.starterkit.data.models.Category
import io.appwrite.starterkit.data.models.QuizResponse
import io.appwrite.starterkit.RetrofitInstance
import io.appwrite.starterkit.viewmodels.QuizSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Parcelable
import androidx.navigation.compose.rememberNavController
import io.appwrite.starterkit.io.appwrite.starterkit.MainScreenCompose
import io.appwrite.starterkit.io.appwrite.starterkit.screens.CustomizePage
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val id: Int, val name: String) : Parcelable

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
//                    MainScreen(account = account) {
//                        val intent = Intent(this, LoginActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//                    }
                    MainScreenCompose()
                }
            }
        }
    }
}
//
//@Composable
//fun MainScreen(account: Account, onLogout: () -> Unit) {
//    val viewModel: QuizSettingsViewModel = viewModel()
//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    val difficulties = listOf(
//        Category(1, "Easy"),
//        Category(2, "Medium"),
//        Category(3, "Hard")
//    )
//    val quiztype = listOf(
//        Category(1, "Multiple Questions"),
//        Category(2, "True/False")
//    )
//    val questionCounts = listOf(
//        Category(5, "5 Questions"),
//        Category(10, "10 Questions"),
//        Category(15, "15 Questions"),
//        Category(20, "20 Questions")
//    )
//
//    var selectedCategory by remember { mutableStateOf<Category?>(null) }
//    var selectedDifficulty by remember { mutableStateOf<Category?>(null) }
//    var selectedQuizType by remember { mutableStateOf<Category?>(null) }
//    var selectedQuestionCount by remember { mutableStateOf<Category?>(null) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF020950))
//    ) {
//        Button(
//            onClick = {
//                coroutineScope.launch(Dispatchers.IO) {
//                    try {
//                        account.deleteSession("current")
//                        onLogout()
//                    } catch (e: AppwriteException) {
//                        // Handle error
//                    }
//                }
//            },
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(30.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF0546AB),
//                contentColor = Color.White
//            )
//        ) {
//            Text("Logout")
//        }
//
//        Column(
//            modifier = Modifier.align(Alignment.Center),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (viewModel.isLoading) {
//                CircularProgressIndicator()
//            } else {
//                CategoryDropdown(
//                    label = "Choose Category",
//                    categories = viewModel.categories,
//                    selected = selectedCategory,
//                    onSelected = { selectedCategory = it }
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CategoryDropdown(
//                label = "Choose Difficulty",
//                categories = difficulties,
//                selected = selectedDifficulty,
//                onSelected = { selectedDifficulty = it }
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CategoryDropdown(
//                label = "Choose Quiz Type",
//                categories = quiztype,
//                selected = selectedQuizType,
//                onSelected = { selectedQuizType = it }
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CategoryDropdown(
//                label = "Number of Questions",
//                categories = questionCounts,
//                selected = selectedQuestionCount,
//                onSelected = { selectedQuestionCount = it }
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(onClick = {
//                coroutineScope.launch {
//                    val response = withContext(Dispatchers.IO) {
//                        RetrofitInstance.api.getQuizQuestions(
//                            amount = selectedQuestionCount?.id ?: 10,
//                            category = selectedCategory?.id,
//                            difficulty = selectedDifficulty?.name?.lowercase(),
//                            type = when (selectedQuizType?.name) {
//                                "True/False" -> "boolean"
//                                "Multiple Questions" -> "multiple"
//                                else -> null
//                            }
//                        )
//                    }
//
//                    if (response.isSuccessful) {
//                        val quizResponse = response.body()
//                        quizResponse?.let {
//                            val intent = Intent(context, QuestionActivity::class.java).apply {
//                                putExtra("QUIZ_RESPONSE", it)
//                            }
//                            context.startActivity(intent)
//                        }
//                    } else {
//                        println("API error: ${response.code()} - ${response.message()}")
//                    }
//                }
//            }) {
//                Text("Start Quiz")
//            }
//        }
//    }
//}
//
//@Composable
//fun CategoryDropdown(
//    label: String,
//    categories: List<Category>,
//    selected: Category?,
//    onSelected: (Category) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    val dropdownWidth = 0.70f
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth(dropdownWidth)
//            .background(Color(0xFF0546AB), shape = RoundedCornerShape(30.dp))
//            .clickable { expanded = true }
//            .padding(horizontal = 14.dp, vertical = 14.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = selected?.name ?: label, color = Color.White)
//            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .fillMaxWidth(0.85f) // <- Ensures same width as Box
//                , shape = RoundedCornerShape(10.dp)
//        ) {
//            categories.forEach { category ->
//                DropdownMenuItem(
//                    text = {
//                        Text(
//                            text = category.name,
//                            color = Color.White // <- White text in dropdown
//                        )
//                    },
//                    onClick = {
//                        onSelected(category)
//                        expanded = false
//                    },
//                    modifier = Modifier
//                        .background(Color(0xFF0546AB))
//                        .fillMaxWidth()
//                )
//            }
//        }
//    }
//}
//
//
//

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            CustomizePage(modifier = Modifier, navController = rememberNavController())
//        }
//    }
//}


