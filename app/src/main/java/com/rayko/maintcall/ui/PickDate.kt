package com.rayko.maintcall.ui

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rayko.maintcall.viewmodel.DetailViewModel
import java.util.Calendar

private val calendar = Calendar.getInstance()
private val currYear = calendar.get(Calendar.YEAR)
private val currMonth = calendar.get(Calendar.MONTH)
private val currDay = calendar.get(Calendar.DAY_OF_MONTH)

@Composable
fun PickDate(
    detailViewModel: DetailViewModel,// = viewModel(),
    onDateSelected: (String) -> Unit
) {

    val context = LocalContext.current
    var pDate = detailViewModel.selectedDate

    DatePickerDialog(
        context,
        {
            DatePicker, pYear, pMonth, pDay ->
            detailViewModel.updateSelectedDate(pYear, pMonth, pDay)
            onDateSelected(pDate)
        }, currYear, currMonth, currDay
    ).show()


}
