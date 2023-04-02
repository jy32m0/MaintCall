package com.rayko.maintcall.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayko.maintcall.ui.screens.*
import com.rayko.maintcall.viewmodel.CallViewModel

@Composable
fun NavigationAppHost(navController: NavHostController) {

    val context = LocalContext.current
    val msg = " Name or Number is missing!"
    val callViewModel = hiltViewModel<CallViewModel>()

    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable(Destination.HomeScreen.route) { HomeScreen(navController) }
        composable(Destination.DBCScreen.route) { DBCScreen(navController, callViewModel) }
        composable(Destination.MiscEquipScreen.route) { navBackStackEntry ->
            val equipName = navBackStackEntry.arguments?.getString("equipDestName")
            if (equipName == null) {
                Toast.makeText(context, "MiscEquip" + msg, Toast.LENGTH_LONG).show()
            } else {
                MiscEquipScreen (navController, callViewModel, eqName = equipName)
            }
        }
        composable(Destination.LogScreen.route) { navBackStackEntry ->
            val equipName = navBackStackEntry.arguments?.getString("equipDestName")
            val equipNum = navBackStackEntry.arguments?.getString("equipDestNum")
            Log.i("NavigationAppHost", "debugging: 36 equipName is $equipName $equipNum")
            if (equipName == null || equipNum == null) {
                Toast.makeText(context, "LogEquipment" + msg, Toast.LENGTH_LONG).show()
            } else {
                LogScreen(navController, callViewModel, equipName, equipNum.toInt())
            }
        }
        composable(Destination.DetailScreen.route) { navBackStackEntry ->
            val logID = navBackStackEntry.arguments?.getLong("logID")
            if (logID == null) {
                Toast.makeText(context, "Detail:" + msg, Toast.LENGTH_LONG).show()
            } else {
                DetailScreen(callViewModel, logID = logID)
            }
        }
    }
}