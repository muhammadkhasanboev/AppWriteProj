package io.appwrite.starterkit.io.appwrite.starterkit.constants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.appwrite.starterkit.io.appwrite.starterkit.CustomizeQuizScreen
import io.appwrite.starterkit.io.appwrite.starterkit.screens.CustomizePage

@Composable
fun Navigator(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "CustomizePage", builder= {
        composable("CustomizePage") {
            CustomizePage(modifier = Modifier , navController)
        }
        composable("CustomizeQuizScreen") {
            CustomizeQuizScreen()
        }
    } )



}


/*
* Setting up navigation for Composable:
* 1. add dependency to build.gradle(app) :
*       dependencies { implementation("androidx.navigation:navigation-compose:2.9.3") }
*
* 2. NavHost - hosts all the screens
*    NavController - navigates through screens
* */