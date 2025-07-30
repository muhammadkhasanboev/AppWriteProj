package io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.*

class LoginActivity : ComponentActivity() {
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val client = Client(this)
            .setEndpoint("https://fra.cloud.appwrite.io/v1")
            .setProject("686f662d00384d0a13b9")

        account = Account(client)

        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                StaticSplashScreen {
                    showSplash = false
                }
            } else {
                LoginPage(
                    onLogin = { email, password ->
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                account.createEmailPasswordSession(email, password)
                                runOnUiThread {
                                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
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
                                account.create("unique()", email, password, name)
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

        // Check for existing session after splash ends
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000) // wait for splash to finish
            try {
                account.get()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } catch (_: AppwriteException) {
                // Do nothing → stay on login page
            }
        }
    }
}

@Composable
fun StaticSplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000) // Show splash for 2 seconds
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash3),
            contentDescription = "Splash Logo",
            modifier = Modifier.size(320.dp)
        )
    }
}
