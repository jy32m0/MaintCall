package com.rayko.maintcall.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rayko.maintcall.clearedTime
import com.rayko.maintcall.convertTime
import com.rayko.maintcall.calledTime
import com.rayko.maintcall.downedTime
import com.rayko.maintcall.ui.TimePicker
import com.rayko.maintcall.ui.theme.postal_blue
import com.rayko.maintcall.ui.theme.postal_red
import com.rayko.maintcall.viewmodel.AlertViewModel
import com.rayko.maintcall.viewmodel.CallViewModelAbstract
import com.rayko.maintcall.viewmodel.DetailViewModel

//import androidx.compose.material.FloatingActionButton

//val showDialog = mutableStateOf(false)
val deleteRec = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)     // for Scaffold
@Composable
fun DetailScreen(
    navController: NavHostController,
    callViewModelDetail: CallViewModelAbstract,
    detailViewModel: DetailViewModel,
    logID: String,
    alertViewModel: AlertViewModel
) {
    val context = LocalContext.current
    val thisCall by callViewModelDetail.getCall(logID.toLong()).collectAsState(initial = null)    //collectAsStateWithLifecycle (initialValue = null)

    Log.i("DetailScreen",
        "debugging: 54: equipment is ${thisCall?.equipType} ${thisCall?.equipNum}")

    var clearedAt = rememberSaveable { 0L }

    var isTimePickerVisible by rememberSaveable { mutableStateOf(false) }
    if (isTimePickerVisible) {
        TimePicker(
            onCancel = {
                Log.i("DetailScreen","debugging: 68: Cancelled")
                isTimePickerVisible = false
            },
            onConfirm = {
                Log.i("DetailScreen","debugging: 65: Confirmed")
                clearedAt = detailViewModel.getMilliTime()    //System.currentTimeMillis()
                Log.i("DetailScreen","debugging: 67: Confirmed, clearedAt = $clearedAt")
                isTimePickerVisible = false
                        },
            content = {}
        )
    }   // if (isTimePickerVisible)

    thisCall?.let { currCall ->

        var reason by rememberSaveable { mutableStateOf( currCall?.callReason ?: "" )} //"Reason: $reasonOf") }
        var solution by rememberSaveable { mutableStateOf( currCall?.clearSolution ?: "") }
        var downedFor = currCall.clearTime - currCall.callTime
        val dateConverted = convertTime(timeCalled = currCall.callTime, form = "dateCalled")
        val calledConverted = calledTime(currCall.callTime)
        var clearedConverted = "* N/C"
        var downedConverted = "* N/C"
        if (currCall.callTime != currCall.clearTime) {
            clearedConverted = clearedTime(currCall.clearTime)
            downedConverted = downedTime(currCall.downTime)
        }

        val textEquip = "${currCall.equipType} ${currCall.equipNum}"

        fun saveThis() {
            currCall.callReason = reason
            currCall.clearSolution = solution
            currCall.clearTime = clearedAt
            currCall.downTime = downedFor
            callViewModelDetail.updateCall(currCall)
        }

        Scaffold(
            floatingActionButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp)
                ) {
                    Column(
                        Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clickable {
                                    alertViewModel.onDelete()
                                },
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            tint = postal_red
                        )
                        Text(
                            text = "Delete",
                            modifier = Modifier.width(40.dp),
                            color = postal_red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        Modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clickable {
                                    saveThis()
                                    navController.popBackStack()
                                },
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save Icon",
                            tint = postal_blue
                        )
                        Text(
                            text = "Save",
                            modifier = Modifier.width(40.dp),
                            color = postal_blue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
        ) {

            Column(  // Main Column
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                val commonModifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                val miscModifier = Modifier
                    .padding(5.dp)
                val rowModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)

                Row(
                    modifier = rowModifier
                ) {
                    Text(
                        text = textEquip,   //"${thisCall.equipType} ${thisCall.equipNum}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = commonModifier
                    )
                    Text(
                        text = dateConverted,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = commonModifier,
                        textAlign = TextAlign.Right
                    )
                }
                Row(
                    modifier = rowModifier
                ) {
                    Text(
                        text = calledConverted,
                        modifier = commonModifier
                    )
                    Text(
                        text = clearedConverted,    // call cleared at
                        modifier = commonModifier
                            .clickable {
                                isTimePickerVisible = true
                                Log.i("DetailScreen","debugging: 209: Clicked, $isTimePickerVisible")
                            }      // add TimePicker for clearedAt
                    )
                }
                Column(
                    modifier = rowModifier
                ) {
                    Text(text = downedConverted, miscModifier)

                    Text("Reason: ", miscModifier)
                    TextField(
                        value = reason,
                        onValueChange = {
                            reason = it
                        },
                        miscModifier,
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                    Text("Solution: ", miscModifier)
                    TextField(
                        value = solution,    // thisCall?.clearSolution ?: "Solution: ",
                        onValueChange = {
                            solution = it
                        },
                        miscModifier,
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }   // Main Column in Scaffold
    }
    // when pressed DELETE
    if (alertViewModel.showDelete) {
        DialogDelete(
            onConfirm = {
                callViewModelDetail.deleteCall(thisCall)
                alertViewModel.onDismissDelete()
                navController.popBackStack()
            },
            onDismiss = { alertViewModel.onDismissDelete()
            }
        )
    }
}

@Preview
@Composable
fun PreviewDetail() {
    var testText by remember { mutableStateOf("")}
    Column() {
        Text(text = "Title: ")
        TextField(
            value = testText,
            onValueChange = { testText = it }
        )
    }
}