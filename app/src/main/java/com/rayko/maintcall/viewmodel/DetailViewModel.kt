package com.rayko.maintcall.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rayko.maintcall.TimePart
import com.rayko.maintcall.calledTime
import com.rayko.maintcall.clearedTime
import com.rayko.maintcall.convertTime
import com.rayko.maintcall.downedTime
import com.rayko.maintcall.timeNow
import com.rayko.maintcall.toDay
import com.rayko.maintcall.ui.TimePicker
import com.rayko.maintcall.ui.screens.DialogDelete
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlinx.coroutines.*

@SuppressLint("DefaultLocale")  /** for format */
class DetailViewModel(
    private val callViewModel: CallViewModelAbstract,    /** Added for 7-17 */
//    logID: Long //= 0L                           /** Added for 7-17 */
): ViewModel() {

    /** BEGIN modification not applied as of 7-17-2023 */

    // create viewModelJob and finish coroutine when onCleared
    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // define a scope for the coroutines to run
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val NOT_CLEARED = "* N/C"
    var clearedConverted by  mutableStateOf(NOT_CLEARED)
    var downedConverted by  mutableStateOf(NOT_CLEARED)
    var clearedAt by  mutableStateOf(0L)
    var reason by  mutableStateOf("")
    var solution by  mutableStateOf("")
    var saveNow by  mutableStateOf(false)
    var dateConverted by  mutableStateOf("")
    var calledConverted by  mutableStateOf("")
    var textEquip by  mutableStateOf("")
    var isTimePickerVisible by mutableStateOf(false)

    @Composable
    fun ToScreen(id: String) {

        val thisCall by callViewModel.getCall(id.toLong())
            .collectAsState(initial = null)    //collectAsStateWithLifecycle (initialValue = null)

        thisCall?.let {

            dateConverted = convertTime(timeCalled = it.callTime, form = "dateCalled")
            calledConverted = calledTime(it.callTime)
            textEquip = "${it.equipType} ${it.equipNum}"

            if (it.callTime != it.clearTime) {
                clearedConverted = clearedTime(it.clearTime)
                downedConverted = downedTime(it.downTime)
            }

            if (saveNow) {
                /** true from TimePicker, confirm */

                if (it.callTime != clearedAt) {
                    it.clearTime = clearedAt
                    clearedConverted = clearedTime(it.clearTime)
                    it.downTime = it.clearTime - it.callTime
                    downedConverted = downedTime(it.downTime)
                }

                it.callReason = reason
                it.clearSolution = solution

                callViewModel.updateCall(it)

                saveNow = false
            }
        }

        /** TimePicker for call clear */
        if (isTimePickerVisible) {
            TimePicker(
                this,
                onCancel = {
                    Log.i("DetailScreen", "debugging: 68: Cancelled")
                    isTimePickerVisible = false
                },
                onConfirm = {
                    Log.i("DetailScreen", "debugging: 65: Confirmed")
                    clearedAt = this.getMilliTime()    //System.currentTimeMillis()
                    Log.i("DetailScreen", "debugging: 67: Confirmed, clearedAt = $clearedAt")
                    saveNow = true
                    isTimePickerVisible = false
                },
                content = {}
            )
        }   // if (isTimePickerVisible)
    }

    /** END modification not applied as of 7-17-2023 */

//    val currCall by callViewModel

    var selectedDate by mutableStateOf(toDay())
        private set

    var selectedDateConvertable by mutableStateOf(listOf(2023, 1, 1))

    var dropDownSelectedHour by mutableStateOf(timeNow(TimePart.Hour))
        private set
    var dropDownSelectedMin by mutableStateOf(timeNow(TimePart.Minute))
        private set

    init {
        uiScope.launch {
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

    /** Called from DetailScreen (onConfirm) to get clear time in milli */
    fun getMilliTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedDateConvertable.get(0),
            selectedDateConvertable.get(1),
            selectedDateConvertable.get(2),
            dropDownSelectedHour,
            dropDownSelectedMin
        )

        Log.i("DetailViewModel", "debugging: selectedDate = $selectedDate")
        Log.i("DetailViewModel", "debugging: dropDownSelectedHour = $dropDownSelectedHour")
        Log.i("DetailViewModel", "debugging: dropDownSelectedMin = $dropDownSelectedMin")

        var milliTime = calendar.timeInMillis

        return milliTime
    }
}


