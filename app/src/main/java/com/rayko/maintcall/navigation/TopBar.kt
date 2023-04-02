package com.rayko.maintcall.ui.screens

import android.text.Layout.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.rayko.maintcall.ui.theme.postal_blue

@Composable
fun TopBar() { //(onMenuClicked: () -> Unit) {
    val navController = rememberNavController()
    TopAppBar(
        title = {
            Text(
                text = "Honolulu Maintenance",
                color = postal_blue,
                fontSize = 24.sp,
            ) },
        backgroundColor = Color.White,
        navigationIcon = {
            Icon(imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
//                modifier = Modifier.clickable(onClick = onMenuClicked),
                tint = Color.Blue
            )
        },

    )
}