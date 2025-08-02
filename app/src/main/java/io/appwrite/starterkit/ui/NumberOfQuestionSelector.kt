package io.appwrite.starterkit.io.appwrite.starterkit.ui

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import io.appwrite.starterkit.R

@Composable
@Preview(showSystemUi = true)
fun NumberOfQuestionsSelector(){
    var numberOfQuestions by rememberSaveable { mutableStateOf(10) }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = numberOfQuestions.toString(),
            onValueChange = {input ->
                numberOfQuestions = input.toIntOrNull() ?: numberOfQuestions
            },
            label = {
                Text(
                    text = stringResource(id = R.string.example_number_of_questions)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor= Color.Black,
                unfocusedTextColor=Color.Black,
                disabledTextColor= Color.Black,
                errorTextColor= Color.Red,
                errorContainerColor= Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFFF5BA69),
                focusedLabelColor = Color(0xFFF5BA69),
                errorIndicatorColor = Color.Red,
                cursorColor = Color(0xFFF5BA69)
            )

        )


    }
}