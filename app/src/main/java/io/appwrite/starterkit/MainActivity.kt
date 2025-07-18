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


    val categories = remember {
        listOf(
            Category(1, "Science"),
            Category(2, "History"),
            Category(3, "Technology"),
            Category(4, "Politics"),
            Category(5, "Celebrities")

        )
    }
    val difficulties = remember {
        listOf(
            Category(1, "Easy"),
            Category(2, "Medium"),
            Category(3, "Hard")
        )
    }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Spinner
        CategoryDropdown(
            categories = categories,
            selected = selectedCategory,
            onSelected = { selectedCategory = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DifficultyDropDown(
            difficulties = difficulties,
            selected = selectedCategory,
            onSelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    account.deleteSession("current")
                    onLogout()
                } catch (e: AppwriteException) {
                    // Optionally show error
                }
            }
        }) {
            Text("Logout")
        }
    }
}
// subject
@Composable
fun CategoryDropdown(
    categories: List<Category>,
    selected: Category?,
    onSelected: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { expanded = true }
            .padding(12.dp)
    ) {
        Text(text = selected?.name ?: "Choose Category")

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

//difficulty
@Composable
fun DifficultyDropDown(
    difficulties: List<Category>,
    selected: Category?,
    onSelected: (Category) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { expanded = true }
            .padding(12.dp)
    ) {
        Text(text = selected?.name ?: "Choose Difficulty")

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            difficulties.forEach { category ->
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

//quiz type


