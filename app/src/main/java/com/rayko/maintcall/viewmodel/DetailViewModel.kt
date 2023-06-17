package com.rayko.maintcall.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayko.maintcall.TimePart
import com.rayko.maintcall.timeNow
import com.rayko.maintcall.toDay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class DetailViewModel(
//    timePart: TimePart = TimePart.Hour
): ViewModel() {

    var selectedDate by mutableStateOf(toDay())
        private set

    var selectedDateConvertable: List<Int> = listOf(2023, 1, 1)

    var dropDownSelectedHour by mutableStateOf(timeNow(TimePart.Hour))
        private set
    var dropDownSelectedMin by mutableStateOf(timeNow(TimePart.Minute))
        private set

    init {
        viewModelScope.launch {
        }
    }

    fun updateSelectedDate(sYear: Int, sMonth: Int, sDay: Int) {
        selectedDate = "${sMonth + 1} - $sDay - $sYear"
        selectedDateConvertable = listOf(sYear, sMonth, sDay)
    }

    fun updateDropDownSelectedHour(selected: Int) {
        dropDownSelectedHour = selected
    }
    fun updateDropDownSelectedMin(selected: Int) {
        dropDownSelectedMin = selected
    }

    fun getMilliTime(): Long {
        Log.i("DetailViewModel", "debugging: 50 into the getMilliTime")
        var calendar = Calendar.getInstance()
        Log.i("DetailViewModel", "debugging: 52 Before calendar.set")
        calendar.set(
            selectedDateConvertable.get(0),
            selectedDateConvertable.get(1),
            selectedDateConvertable.get(2),
            dropDownSelectedHour,
            dropDownSelectedMin
        )
        Log.i("DetailViewModel", "debugging: 60 Before var milliTime " +
                selectedDateConvertable.get(0) + "-" +
                selectedDateConvertable.get(1) + "-" +
                selectedDateConvertable.get(2) + " " +
                dropDownSelectedHour + ":" +
                dropDownSelectedMin)
        Log.i("DetailViewModel", "debugging: selectedDate = $selectedDate")
        Log.i("DetailViewModel", "debugging: dropDownSelectedHour = $dropDownSelectedHour")
        Log.i("DetailViewModel", "debugging: dropDownSelectedMin = $dropDownSelectedMin")
        var milliTime = calendar.timeInMillis
        Log.i("DetailViewModel", "debugging: 67 time is " + milliTime)
        return milliTime
    }

}


