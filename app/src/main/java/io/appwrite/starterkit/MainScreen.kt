package io.appwrite.starterkit.io.appwrite.starterkit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.appwrite.starterkit.io.appwrite.starterkit.data.NavItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.starterkit.LoginActivity

import io.appwrite.starterkit.R
import io.appwrite.starterkit.io.appwrite.starterkit.constants.Navigator
import io.appwrite.starterkit.io.appwrite.starterkit.screens.CustomizePage
import io.appwrite.starterkit.io.appwrite.starterkit.screens.RankingPage
import io.appwrite.starterkit.io.appwrite.starterkit.screens.SettingsPage



@Composable
fun MainScreenCompose( modifier: Modifier = Modifier){
    val navItemList = listOf(
        NavItem("Home", iconPainter = painterResource(id = R.drawable.home_24)),
        NavItem("Rankings", iconPainter = painterResource(id = R.drawable.rank_24)),
        NavItem("Settings", iconPainter = painterResource(id = R.drawable.settings_24))
    )
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 3.dp,end = 3.dp, bottom = 5.dp),
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(percent = 90),
                tonalElevation = 8.dp,
                shadowElevation = 10.dp,
                modifier = Modifier.height(80.dp)
            ) {
            NavigationBar(
                containerColor = Color(0xFFF5BA69),

            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        modifier = modifier.padding(top=10.dp),
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index},
                        icon ={
                            Icon(
                                painter = navItem.iconPainter,
                                contentDescription = navItem.label
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = Color(0xFFF5BA69),
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black

                        )
                        )
                }
            }
            }
        }
    ) {
        innerPadding -> ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "CustomizePage",
                modifier = modifier
            ) {
                composable("CustomizePage") {
                    CustomizePage(navController = navController)
                }
                composable("CustomizeQuizScreen") {
                    CustomizeQuizScreen()
                }
            }
        }
        1 -> RankingPage()
        2 -> SettingsPage()
        else -> {
            // defensive fallback so you don't hit unhandled states
            Text("Unknown tab: $selectedIndex")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun mainscreenprev(){
    MainScreenCompose()
}