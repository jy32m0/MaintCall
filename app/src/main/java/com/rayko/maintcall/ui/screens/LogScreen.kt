package com.rayko.maintcall.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rayko.maintcall.CalledDate
import com.rayko.maintcall.CalledTime
import com.rayko.maintcall.ClearedTime
import com.rayko.maintcall.ConvertTime
import com.rayko.maintcall.downedTime
import com.rayko.maintcall.navigation.Destination
import com.rayko.maintcall.viewmodel.CallViewModelAbstract
import java.text.SimpleDateFormat
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)     // Scaffold
@Composable
fun LogScreen (
    navController: NavHostController,
    callViewModelLog: CallViewModelAbstract,
    equipName: String, equipNum: Int
) {
    val context = LocalContext.current
    val callListState = callViewModelLog.callListFlow.collectAsState(initial = listOf())

    Log.i("LogScreen", "debugging: 43:  equipName is $equipName $equipNum")

    @Composable
    fun RowScope.headerText(txt: String) {
        Text ( text = txt,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f)
        )
    }

    @Composable
    fun RowScope.bodyText(txt: String) {
        Text ( text = txt,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }


    Scaffold {
        LazyColumn {
            items(
                callListState.value.size
            ) { index ->

                val call = callListState.value[index]
                val dateDownAt = CalledDate(time = call.callTime)
                val calledConverted = CalledTime(time = call.callTime)
                var clearedConverted = "* NOT CLEARED"
                var downedConverted = "* NOT CLEARED"

                if (call.callTime != call.clearTime) {
                    clearedConverted = ClearedTime(call.clearTime)
                    downedConverted = downedTime(time = call.downTime)
//                    downedConverted = "$downed ${ConvertTime(timeDowned = call.downTime / 1000, form = "diff")}"
                }

                Log.i("LogScreen", "debugging: 80: equipName is $equipName")

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(120.dp)
                        .clickable {
                            navController.navigate(
                                Destination.DetailScreen.routeToDetail(call.roomId)
                            )
                        }
                ) {
                    Row() {
                        headerText (call.equipType + " " + call.equipNum)
                        headerText ( dateDownAt )   // Date the call was placed
                    }
                    Row() {
                        bodyText(calledConverted)
                        bodyText(clearedConverted)
                    }
                    Row() {
                        bodyText(downedConverted)
                    }
                    Row() {
                        bodyText(txt = "${call.callReason} - ${call.clearSolution}")
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun PreviewLogScreen() {
    val dateFormat = SimpleDateFormat("MM / dd / yyyy")
    val timeFormat = SimpleDateFormat("HH:mm:ss")
    val text: String = dateFormat.format(1678144183980)
    val text2: String = timeFormat.format(77962129)

    val timeDiff = (1678225600900 - 1678219200000) / 1000.0
    var hour = floor(timeDiff / 3600).toInt()
    var min = floor((timeDiff % 3600) / 60).toInt()

    val downTime: Long = 609054704 / 1000
    hour = floor(downTime / 3600.00).toInt()
    min = floor((downTime % 3600.00) / 60.00).toInt()

    var text3 = ""  //""Total Down-Time:"
    if (hour < 1) {
        text3 = "$text3 $min Minutes"
    } else {
        text3 = "$text3 $hour Hour $min Minutes"
    }

    Row( modifier = Modifier) {
        Text(
            text3,
            color= Color.White,
            modifier = Modifier.weight(2f)
        )
//        Text(text3.toString(), color=Color.White,
//            modifier = Modifier.weight(1f))
    }

//    LogScreen(
//        navController= rememberNavController(),
//        callViewModelLog = object : CallViewModelAbstract {
//            override val callListFlow: Flow<List<CallEntity>>
//                get() = TODO("Not yet implemented")
//
//            override fun getLastCall(call: CallEntity) {
//                TODO("Not yet implemented")
//            }
//
//            override fun insertCall(call: CallEntity) {
//                TODO("Not yet implemented")
//            }
//
//            override fun updateCall(call: CallEntity) {
//                TODO("Not yet implemented")
//            }
//
//            override fun deleteCall(call: CallEntity) {
//                TODO("Not yet implemented")
//            }
//
//            override fun deleteAllCalls() {
//                TODO("Not yet implemented")
//            }
//        },
//        "Equip Name",
//        2
//    )

}