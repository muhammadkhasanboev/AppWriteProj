package io.appwrite.starterkit.io.appwrite.starterkit.ui

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.appwrite.starterkit.R
import java.nio.file.WatchEvent

@Composable
fun LoginPage(
    onLogin: (String, String) -> Unit,
    onSignup: (String, String, String) -> Unit
){
    var isLogin by remember { mutableStateOf(true) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //login page image
        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = "login",
            modifier = Modifier.size(200.dp)
            )
        //text : welcome back
        Text(
            text = stringResource(id = R.string.login_page_welcome_text),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        //text: login or sign up
//        Text(
//            text = if(isLogin){ stringResource(R.string.login)}
//            else {stringResource(R.string.signup)},
//            style = TextStyle(
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Medium
//            )
//        )

        Spacer(modifier = Modifier.height(10.dp))
//on sign-up requests for name
        if (!isLogin) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text=stringResource(id = R.string.name)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = {
                Text(text=stringResource(id = R.string.email) )
            },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(8.dp))
//password text field
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(
                    text = stringResource(id = R.string.password)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )





    }
}

@Composable
@Preview(showSystemUi = true)
fun LoginPagePreview(){
    LoginPage(
        onLogin = { email, password ->
            // You can log or print something here for preview
            println("Login clicked: $email, $password")
        },
        onSignup = { name, email, password ->
            println("Signup clicked: $name, $email, $password")
        }
    )
}