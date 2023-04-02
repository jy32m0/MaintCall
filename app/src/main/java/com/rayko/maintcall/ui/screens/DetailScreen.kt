package com.rayko.maintcall.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rayko.maintcall.viewmodel.CallViewModelAbstract

@Composable
fun DetailScreen(
    callViewModelDetail: CallViewModelAbstract,
    logID: Long
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    Log.i("DetailScreen", "debugging: 22 logID = " + logID)

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable {  }
    ) {
        val commonModifier = Modifier
            .padding(5.dp)
            .weight(1f)
        val miscModifier = Modifier
            .padding(5.dp)

        Row(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "calls.equipType +  + calls.equipNum",
                fontSize = 20.sp,
                modifier = commonModifier
            )
            Text(
                text = "convertMillis(calls.callTime, date",
                fontSize = 20.sp,
                modifier = commonModifier,
                textAlign = TextAlign.Right
            )
        }

        Row(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Called: " + "convertMillis(calls.callTime, 'time')",
                modifier = commonModifier
            )
            Text(
                text = "Cleared: ",
                modifier = commonModifier
            )
        }

        Text(text = "Down-time: ", miscModifier)
        Text(text = "Reason: ", miscModifier)
        Text(text = "Solution: ", miscModifier)
    }

    Toast.makeText(context, "Detail Screen - " + logID, Toast.LENGTH_LONG).show()
}
