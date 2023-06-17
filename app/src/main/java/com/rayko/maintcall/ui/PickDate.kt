package com.rayko.maintcall.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rayko.maintcall.viewmodel.DetailViewModel
import java.util.Calendar

private val backgroundColor = Color(49, 52, 58)
private val calendar = Calendar.getInstance()
private val currYear = calendar.get(Calendar.YEAR)
private val currMonth = calendar.get(Calendar.MONTH)
private val currDay = calendar.get(Calendar.DAY_OF_MONTH)

@Composable
fun PickDate(
    detailViewModel: DetailViewModel = viewModel(),
    onDateSelected: (String) -> Unit
) {

    val context = LocalContext.current
//    var pDate by rememberSaveable { mutableStateOf("date") }
    var pDate = detailViewModel.selectedDate

    DatePickerDialog(
        context,
        {
            DatePicker, pYear, pMonth, pDay ->
//            pDate = "${pMonth + 1}-$pDay-$pYear"
            detailViewModel.updateSelectedDate(pYear, pMonth, pDay)
            onDateSelected(pDate)
        }, currYear, currMonth, currDay
    ).show()


}

//@Preview(showBackground = true)
@Composable
fun Pview(detailViewModel: DetailViewModel,) {
    val selectedDate = remember { mutableStateOf("") }
    PickDate { date ->
        selectedDate.value = date
    }
}