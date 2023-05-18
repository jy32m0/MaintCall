package com.rayko.maintcall.ui

import android.icu.text.CaseMap.Title
import android.util.Log
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rayko.maintcall.TimeCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.rayko.maintcall.ui.PickDate
import com.rayko.maintcall.TimePart
import com.rayko.maintcall.toDay


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePicker(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    var pickedHour by rememberSaveable { mutableStateOf(0) }
    var pickedMin by rememberSaveable { mutableStateOf(0) }
    var pickedPart by rememberSaveable { mutableStateOf(TimePart.Hour) }
    var pickedDate by rememberSaveable { mutableStateOf(toDay()) }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    if (showDatePicker) {
        PickDate { date ->
            pickedDate = date
        }
        showDatePicker = false
    }

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {
        Surface(
//            shape = MaterialTheme.shapes.large,
//            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = Color.Yellow //MaterialTheme.colors.surface
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                content()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = pickedDate,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true) { showDatePicker = true },
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    TimeCard(
                        time = pickedHour,
                        isSelected = pickedPart == TimePart.Hour,
                        onClick = { pickedPart = TimePart.Hour }
                    )
                    Text(text = ":", fontSize = 26.sp)
                    TimeCard(
                        time = pickedMin,
                        isSelected = pickedPart == TimePart.Minute,
                        onClick = { pickedPart = TimePart.Minute }
                    )
                }

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Preview
@Composable
fun showPicker() {
    TimePicker(
        onCancel = { Log.i("TimePicker","Preview Cancel") },
        onConfirm = { Log.i("TimePicker","Preview Confirm") },
        toggle = { Log.i("TimePicker","Preview toggle") },
        content = { Log.i("TimePicker","Preview content") }
    )
}