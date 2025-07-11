package io.appwrite.starterkit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    onLogin: (String, String) -> Unit,
    onSignup: (String, String, String) -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isLogin) "Login" else "Sign Up",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLogin) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isLogin) {
                    onLogin(email, password)
                } else {
                    onSignup(name, email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLogin) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (isLogin) "Don't have an account? Sign up" else "Already have an account? Log in",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { isLogin = !isLogin }
        )
    }
}
