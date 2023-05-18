package com.rayko.maintcall.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.rayko.maintcall.viewmodel.CallViewModelAbstract
import kotlinx.coroutines.flow.Flow

@Composable
fun DBCScreen(
    navController: NavHostController,
    callViewModelDB: CallViewModelAbstract,
) {

    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val state = rememberLazyListState()

    val dbIds: List<String> = listOf("CIOSS 02", "DBCS 01", "DBCS 02", "DBCS 05", "DBCS 09", "DBCS 10", "DBCS 12",
        "DBCS 13", "DBCS 17", "DBCS 18", "DBCS 19", "DBCS 20", "DIOSS 15", "DIOSS 16")

    fun navTo (equipment: String) {
        var eqName = "DBCS"
        var eqNum = "00"
        if (equipment.subSequence(0, 4) == "DBCS") {
            eqNum = equipment.drop(5)
        } else {
            eqName = equipment.subSequence(0, 5).toString()
            eqNum = equipment.drop(6)
        }

        callViewModelDB.insertCall(
            CallEntity(equipType = eqName, equipNum = eqNum.toInt())
        )

        navController.navigate(Destination.LogScreen.routeToLog(eqName, eqNum))
    }

    LazyColumn(
        state = state,
//        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dbIds) { dbId ->
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .padding(horizontal = 45.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20))
                .background(color = postal_blue)
                .clickable { navTo(dbId) }
            ) {
                Text(
                    text = dbId,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun previewDB() {
//    DBCScreen(
//        navController = rememberNavController(),
//        callViewModelDB = object : CallViewModelAbstract {
//            override val callListFlow: Flow<List<CallEntity>>
//                get() = TODO("Not yet implemented")
//
////            override fun getLastCall(call: CallEntity) {
////                TODO("Not yet implemented")
////            }
////
////            override fun getCall(id: Long) {
////                TODO("Not yet implemented")
////            }
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
//        }
//    )
}