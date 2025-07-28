package io.appwrite.starterkit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .fillMaxSize()
            .padding(24.dp),
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
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { /*TODO*/ })
                {
                    Text("Show")
                }
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(isLogin){
                    onLogin(email, password)
                }else{
                    onSignup(name, email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF5BA69),
                contentColor = Color(0xFF0D0D0C))
        ){
            Text(
                text = if(isLogin){ stringResource(R.string.login)}
                else {stringResource(R.string.signup)},

                )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if(isLogin){stringResource(id = R.string.signup_request)}
            else{stringResource(id = R.string.login_request)},
            color = Color(0xFF0D0D0C),
            modifier = Modifier.clickable { isLogin = !isLogin }
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