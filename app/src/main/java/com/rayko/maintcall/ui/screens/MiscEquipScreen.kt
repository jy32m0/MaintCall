package com.rayko.maintcall.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rayko.maintcall.data.CallEntity
import com.rayko.maintcall.navigation.Destination
import com.rayko.maintcall.ui.theme.postal_blue
import com.rayko.maintcall.viewmodel.CallViewModelAbstract

@Composable
fun MiscEquipScreen(
    navController: NavController,
    callViewModelMisc: CallViewModelAbstract,
    eqName: String
) {
    val context = LocalContext.current
//    val callListState = callViewModelMisc.callListFlow.collectAsState(initial = listOf())
    val state = rememberLazyListState()

    // number of each equipment
    val equipNumMax = when (eqName) {
        "AFSM" -> 1
        "AFCS" -> 2
        "APBS" -> 3
        else -> 2
    }

    fun navTo(eqNum: Int) {
        val newLog = CallEntity(equipType = eqName, equipNum = eqNum)
        callViewModelMisc.insertCall(newLog)
        Log.i("MiscScreen", "debugging: 47 eqName is $eqName $eqNum")
        navController.navigate(Destination.LogScreen.routeToLog(eqName, eqNum.toString()))
    }


    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(equipNumMax) { index ->
            val eqItem = index + 1
            Spacer(modifier = Modifier.height(35.dp))
            Box(modifier = Modifier
                .padding(horizontal = 45.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20))
                .background(color = postal_blue)
                .clickable { navTo(eqItem) }
            ) {
                Text(
                    text = "$eqName $eqItem",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp)
                )
            }


//            Row(
//                modifier = Modifier
//                    .height(60.dp)
//                    .clickable { navTo(eqItem) },
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    eqName + " " + eqItem.toString(),
//                    fontSize = 22.sp,
//                    color = Color.Blue,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
        }
    }
}
