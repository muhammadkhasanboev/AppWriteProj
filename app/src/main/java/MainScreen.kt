package io.appwrite.starterkit

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialogDefaults.containerColor
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


@Composable
fun MainScreen(modifier: Modifier = Modifier){
    val navItemList = listOf(
        NavItem("Customize", iconPainter = painterResource(id = R.drawable.home_24)),
        NavItem("Rankings", iconPainter = painterResource(id = R.drawable.rank_24)),
        NavItem("Settings", iconPainter = painterResource(id = R.drawable.settings_24))
    )
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(percent = 90),
                tonalElevation = 8.dp,
                shadowElevation = 10.dp,
                modifier = Modifier.height(100.dp)
            ) {
            NavigationBar(
                containerColor = Color(0xFFF5BA69),

            ) {
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
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFF5BA69),
                            selectedTextColor = Color.White,
                            indicatorColor = Color.White,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black

                        )
                        )
                }
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