package com.rayko.maintcall.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
//val deleteRec = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)     // for Scaffold
@Composable
fun DetailScreen(
    navController: NavHostController,
    callViewModelDetail: CallViewModelAbstract,
    logID: Long,
    alertViewModelDetail: AlertViewModel
) {
    val context = LocalContext.current
    val thisCall by callViewModelDetail.getCall(logID).collectAsState(initial = null)
    var text by remember { mutableStateOf("") }

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
                                    if (deleteRec.value) {
                                        callViewModelDetail.deleteCall(thisCall)
                                        navController.popBackStack()
                                    }
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
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(10.dp))
            ) {
                val commonModifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                val miscModifier = Modifier
                    .padding(5.dp)

                var clearedAt = ConvertTime(thisCall.clearTime, "time")
                var downedFor = ConvertTime(thisCall.downTime, "time")
                if (thisCall.callTime == thisCall.clearTime) {
                    clearedAt = "* N/C"
                    downedFor = "* N/C"
                }

                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${thisCall.equipType} ${thisCall.equipNum}",
                        fontSize = 20.sp,
                        modifier = commonModifier
                    )
                    Text(
                        text = ConvertTime(thisCall.callTime, "date"),
                        fontSize = 20.sp,
                        modifier = commonModifier,
                        textAlign = TextAlign.Right
                    )
                }

                Row(
                    Modifier.fillMaxWidth()
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

                Text(text = "Down-time = $downedFor", miscModifier)
                Text(text = "Reason: ${thisCall.callReason}", miscModifier)
                Text(text = "Solution: ${thisCall.clearSolution}", miscModifier)
            }
        }
    }
    if (alertViewModelDetail.showAlert) {

    }
}

//@Composable
//fun DialogDelete() {
//    AlertDialog(
//        onDismissRequest = {
//            showDialog.value = false
//        },
//        title = {
//            Text(text = "Title")
//        },
//        text = {
//            Text("Custom Text")
//        },
//        confirmButton = {
//            androidx.compose.material.Button(
//                onClick = { deleteRec.value = true}
//            ) {
//                Text(text = "DELETE")
//            }
//        }
//    )
//}


@Preview
@Composable
fun PreviewDetail() {

}