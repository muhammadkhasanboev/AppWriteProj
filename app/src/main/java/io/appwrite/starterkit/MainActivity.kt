package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔗 Recreate Appwrite client (needed here too)
        val client = Client(this)
            .setEndpoint("https://fra.cloud.appwrite.io/v1")
            .setProject("686f662d00384d0a13b9")

        account = Account(client)

        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                HelloWithLogout(account = account, onLogout = {
                    // 🚪 Redirect to LoginActivity and clear back stack
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun HelloWithLogout(account: Account, onLogout: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Hello")

            Spacer(modifier = Modifier.height(16.dp))

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
}
