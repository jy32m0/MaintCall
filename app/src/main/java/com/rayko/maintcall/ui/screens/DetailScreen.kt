package com.rayko.maintcall.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rayko.maintcall.ui.theme.postal_blue
import com.rayko.maintcall.ui.theme.postal_red
import com.rayko.maintcall.viewmodel.AlertViewModel
import com.rayko.maintcall.viewmodel.CallViewModelAbstract

//import androidx.compose.material.FloatingActionButton

//val showDialog = mutableStateOf(false)
val deleteRec = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)     // for Scaffold
@Composable
fun DetailScreen(
    navController: NavHostController,
    callViewModelDetail: CallViewModelAbstract,
    logID: String,
    alertViewModelDetail: AlertViewModel
) {
    var count : Int = 0
    Log.i("DetailScreen", "debugging: Ln 44 count = $count++")
    val context = LocalContext.current
    val thisCall by callViewModelDetail.getCall(logID.toLong()).collectAsState(initial = null)
    Log.i("DetailScreen", "debugging: Ln 47 count = $count , ${thisCall?.callReason}")

    val reasonOf = thisCall?.callReason ?: ""
    Log.i("DetailScreen", "debugging: Ln 50 count = $count , ${thisCall?.callReason}")
    val solutionOf = thisCall?.clearSolution ?: ""
    Log.i("DetailScreen", "debugging: Ln 52 count = $count , ${thisCall?.clearSolution}")
    var reason by remember { mutableStateOf("Reason: $reasonOf") }
    Log.i("DetailScreen", "debugging: Ln 54 count = $count , $reason")
    var solution by remember { mutableStateOf("Solution: $solutionOf") }
    Log.i("DetailScreen", "debugging: Ln 56 count = $count , $solution")

    thisCall?.let { thisCall ->
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
                                    alertViewModelDetail.onDelete()
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
                                    Log.i("DetailScreen", "debugging: Ln 97 count = $count , ${thisCall.callReason}")
//                                    thisCall.callReason = reason
//                                    thisCall.clearSolution = solution
                                    callViewModelDetail.updateCall(thisCall)
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
            Column(modifier = Modifier
                .fillMaxSize()
//                .clip(shape = RoundedCornerShape(10.dp))
            ) {
                val commonModifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                val miscModifier = Modifier
                    .padding(5.dp)
                val rowModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)

                var clearedAt = ConvertTime(thisCall.clearTime, "time")
                var downedFor = ConvertTime(thisCall.downTime, "time")
                if (thisCall.callTime == thisCall.clearTime) {
                    clearedAt = "* N/C"
                    downedFor = "* N/C"
                }

                Row(
                    modifier = rowModifier
                ) {
                    Text(
                        text = "${thisCall.equipType} ${thisCall.equipNum}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = commonModifier
                    )
                    Text(
                        text = ConvertTime(thisCall.callTime, "date"),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = commonModifier,
                        textAlign = TextAlign.Right
                    )
                }

                Row(
                    modifier = rowModifier
                        .clickable {  }     // add TimePicker for clearedAt
                ) {
                    Text(
                        text = "Called @ ${ConvertTime(thisCall.callTime, "time")}",
                        modifier = commonModifier
                    )
                    Text(
                        text = "Cleared @ $clearedAt",
                        modifier = commonModifier
                    )
                }
                Column(
                    modifier = rowModifier
                ) {
                    Text(text = "Down-time = $downedFor", miscModifier)
                }
                Row(
                    modifier = rowModifier
                ) {
                    TextField(
                        value = reason,
                        onValueChange = { newReason ->
                            reason = newReason
                        },
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
                Row(
                    modifier = rowModifier
                ) {
                    TextField(value = solution,
                        onValueChange = { newSolution ->
                            solution = newSolution
                        },
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }
    // when pressed DELETE
    if (alertViewModelDetail.showAlert) {
        DialogDelete(
            onConfirm = {
                callViewModelDetail.deleteCall(thisCall)
                alertViewModelDetail.onDismissAlert()
                navController.popBackStack()
            },
            onDismiss = { alertViewModelDetail.onDismissAlert()
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