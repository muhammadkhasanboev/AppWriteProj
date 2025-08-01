package io.appwrite.starterkit.io.appwrite.starterkit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.appwrite.starterkit.R


data class SubjectItem(
    val name: String,
    val categoryId: Int,
    val iconRes: Int)


@Composable
fun CustomizePage(modifier: Modifier = Modifier, navController: NavController){
   val itemsList = listOf(
       SubjectItem("Math", 1, R.drawable.math),
       SubjectItem("Science & Nature", 2, R.drawable.science),
       SubjectItem("Animals", 3, R.drawable.animals),
       SubjectItem("General Knowledge", 4, R.drawable.general),
       SubjectItem("Books", 5, R.drawable.books),
       SubjectItem("Geography", 6, R.drawable.geography),
       SubjectItem("Art", 7, R.drawable.art),
       SubjectItem("Video Games", 8, R.drawable.gaming),
       SubjectItem("Sports", 9, R.drawable.sports),
       SubjectItem("Computers", 10, R.drawable.computer),
       SubjectItem("History", 11, R.drawable.history),
       SubjectItem("Vehicles", 12, R.drawable.vehicles)
   )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top=20.dp, bottom = 60.dp )
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().fillMaxWidth(),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(16.dp),
            userScrollEnabled = true
        ) {
            items(itemsList) { item ->
                SubjectCard(subject = item, onClick = {
                    navController.navigate("CustomizeQuizScreen")
                })
            }

        }
}}

@Composable
fun SubjectCard(subject: SubjectItem,
                onClick: (SubjectItem)->Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable {onClick(subject) }
            .background(Color(0xFFD1D3D3))
            .height(150.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = subject.iconRes),
            contentDescription = subject.name,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = subject.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview(showSystemUi = true)
fun prev(){
    val navController = rememberNavController()
    CustomizePage(navController = navController)
}

