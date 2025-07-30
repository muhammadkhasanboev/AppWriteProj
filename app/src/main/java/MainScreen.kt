package io.appwrite.starterkit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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


@Composable
fun MainScreen(modifier: Modifier = Modifier){
    val navItemList = listOf(
        NavItem("Customize", iconPainter = painterResource(id = R.drawable.home_24)),
        NavItem("Rankings", iconPainter = painterResource(id = R.drawable.rank_24)),
        NavItem("Settings", iconPainter = painterResource(id = R.drawable.settings_24))
    )
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
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
                        }
                        )
                }
            }
        }
    ) {
        innerPadding -> ContentScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier){

}
@Composable
@Preview(showSystemUi = true)
fun mainscreenprev(){
    MainScreen()
}