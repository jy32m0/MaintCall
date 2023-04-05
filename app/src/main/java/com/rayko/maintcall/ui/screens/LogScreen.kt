package com.rayko.maintcall.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rayko.maintcall.navigation.Destination
import com.rayko.maintcall.viewmodel.CallViewModelAbstract
import com.rayko.maintcall.R
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

    Log.i("LogScreen", "debugging: 35 equipName is $equipName $equipNum")

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
            items(callListState.value.size) { index ->
                val call = callListState.value[index]
                val dateDownAt = ConvertTime(call.callTime, "date")
                val called = stringResource(id = R.string.called)
                val cleared = stringResource(id = R.string.cleared)
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
                        headerText (  dateDownAt)   // Date the call was placed
                    }
                    Row() {
                        bodyText(called + ConvertTime(call.callTime, "time"))
                        if (call.callTime == call.clearTime) {
                            bodyText(txt = cleared)
                        } else {
                            bodyText(cleared + ConvertTime(call.clearTime, "time"))
                        }
                    }
                    Row() {
                        bodyText(txt = "Summary: ")
                    }
                }
            }
        }
    }
    Toast.makeText(context, "Log Scree you're in", Toast.LENGTH_LONG).show()
}

fun ConvertTime(time: Long, form: String): String {
    val dateFormat = SimpleDateFormat("MM/ dd/ yyyy")
    val timeFormat = SimpleDateFormat("HH:mm:ss")
    var converted = "??"
    when (form) {
        "date" -> converted = dateFormat.format(time)
        "time" -> converted = timeFormat.format(time)
    }

    fun timeDiff() {
        val diff = (1678225600900 - 1678219200000) / 1000.0
        val hour = floor(diff / 3600).toInt()
        val min = floor((diff % 3600) / 60).toInt()
        converted = if (hour < 1) {
            "$min Minutes"
        } else {
            "$hour Hour $min Minutes"
        }
    }
    return converted
}

@Preview
@Composable
fun PreviewLogScreen() {
    val dateFormat = SimpleDateFormat("MM / dd / yyyy")
    val timeFormat = SimpleDateFormat("HH:mm:ss")
    val text: String = dateFormat.format(1678144183980)
    val text2: String = timeFormat.format(1678144183980)

    val timeDiff = (1678225600900 - 1678219200000) / 1000.0
    val hour = floor(timeDiff / 3600).toInt()
    val min = floor((timeDiff % 3600) / 60).toInt()
    var text3 = "Total Down-Time"
    if (hour < 1) {
        text3 = "$min Minutes"
    } else {
        text3 = "$hour Hour $min Minutes"
    }

    Row( modifier = Modifier) {
        Text(
            text,
            color= Color.White,
            modifier = Modifier.weight(2f)
        )
        Text(text3.toString(), color=Color.White,
            modifier = Modifier.weight(1f))
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