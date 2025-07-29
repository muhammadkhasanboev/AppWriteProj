package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.appwrite.starterkit.LoginPage

class LoginActivity : ComponentActivity() {

    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Appwrite client
        val client = Client(this)
            .setEndpoint("https://fra.cloud.appwrite.io/v1")
            .setProject("686f662d00384d0a13b9")

        account = Account(client)

        // Step 1: Check session BEFORE showing login/signup
        CoroutineScope(Dispatchers.IO).launch {
            try {
                account.get()  // Will throw if no session
                // Session exists → go directly to MainActivity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } catch (e: AppwriteException) {
                // No session → Show AuthScreen UI
                runOnUiThread {
                    showAuthScreen()
                }
            }
        }
    }

   // login/signup UI
    private fun showAuthScreen() {
        setContent {
            LoginPage(
                onLogin = { email, password ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            account.createEmailPasswordSession(email, password)
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                                //  Go to MainActivity after successful login
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }
                        } catch (e: AppwriteException) {
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                onSignup = { name, email, password ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            account.create(
                                userId = "unique()",
                                email = email,
                                password = password,
                                name = name
                            )
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Signup successful! Now login.", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: AppwriteException) {
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Signup failed: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            )
        }
    }
}
