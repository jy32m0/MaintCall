package com.rayko.maintcall.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rayko.maintcall.data.CallEntity
import com.rayko.maintcall.navigation.Destination
import com.rayko.maintcall.ui.theme.postal_blue
import com.rayko.maintcall.viewmodel.CallViewModel
import com.rayko.maintcall.viewmodel.CallViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)     // for Scaffold
@Composable
fun HomeScreen (navController: NavHostController) {

    val context = LocalContext.current
    val equipName: List<String> = listOf("AFCS", "AFSM", "APBS", "DB/C/DIOSS", "OTHERS")

    fun navTo(dest: Int) {
        when(dest) {
            in 0 .. 2 -> navController.navigate(
                Destination.MiscEquipScreen.routeToMisc(equipName.get(dest))
            )
            3 -> navController.navigate(Destination.DBCScreen.route)
            else -> Toast.makeText(context,
                "Reserved for future development. Please select another.",
                    Toast.LENGTH_LONG).show()
        }
    }

    Scaffold (
        topBar = { TopBar() },
    ){ padding ->
        LazyColumn (
            modifier = Modifier.padding(padding)
                ) {
            items(equipName.size) { index ->
                Spacer(modifier = Modifier.height(35.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .clip(RoundedCornerShape(20))
                    .background(color = postal_blue)
                    .clickable { navTo(index) }
                ) {
                    Text(
                        equipName.get(index),
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())

}