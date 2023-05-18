package com.rayko.maintcall.ui

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Date

private val backgroundColor = Color(49, 52, 58)
private val calendar = Calendar.getInstance()
private val currYear = calendar.get(Calendar.YEAR)
private val currMonth = calendar.get(Calendar.MONTH)
private val currDay = calendar.get(Calendar.DAY_OF_MONTH)

@Composable
fun PickDate(onDateSelected: (String) -> Unit) {

    val context = LocalContext.current
    var pDate by rememberSaveable { mutableStateOf("date") }

    DatePickerDialog(
        context,
        {
            DatePicker, pYear, pMonth, pDay ->
            pDate = "${pMonth + 1}-$pDay-$pYear"
            onDateSelected(pDate)
        }, currYear, currMonth, currDay
    ).show()

}

@Preview(showBackground = true)
@Composable
fun Pview() {
    val selectedDate = remember { mutableStateOf("") }
    PickDate { date ->
        selectedDate.value = date
    }
}