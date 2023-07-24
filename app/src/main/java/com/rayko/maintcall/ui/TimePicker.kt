package com.rayko.maintcall.ui

//import android.text.format.DateFormat.format
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rayko.maintcall.ExposedDropdownMenu
import com.rayko.maintcall.TimePart
import com.rayko.maintcall.toDay
import com.rayko.maintcall.viewmodel.DetailViewModel
import java.lang.String.format


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePicker(
    detailViewModel: DetailViewModel,// = viewModel(),
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    var pickedDate = detailViewModel.selectedDate
    val pickedHour = format("%02d", detailViewModel.dropDownSelectedHour)
    val pickedMin = format("%02d", detailViewModel.dropDownSelectedMin)

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    if (showDatePicker) {
        PickDate(detailViewModel) { date ->
            pickedDate = date
        }
        showDatePicker = false
    }

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {
        Surface(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = Color.Yellow //MaterialTheme.colors.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(   /** "Select Time" */
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )

                Box(        /** Date */
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
                }           /** Date */

                Row(    /** Hour and Minute TimeCards */
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    ExposedDropdownMenu(    /** Hour TimeCard */
                        detailViewModel,
                        items = (1..23).toList(),
                        selectedItem = pickedHour,
//                        selectedItem = format("%02d", detailViewModel.dropDownSelectedHour),  //selectedNumber,
                        selectedItemChanged = {
                            detailViewModel.updateDropDownSelectedHour(it.toInt())
                        },
                        label = TimePart.Hour.toString()  // { Text("Hour") }
                    )                       /** Hour TimeCard */

                    Text(
                        text = ":",
                        fontSize = 40.sp,
                        modifier = Modifier.offset(y = 5.dp)
                    )
//                    Spacer(modifier = Modifier.width(16.dp))

                    ExposedDropdownMenu(    /** Minute TimeCard */
                        detailViewModel,
                        items = (1..59).toList(),
                        selectedItem = pickedMin, // format("%02d", detailViewModel.dropDownSelectedMin),
                        selectedItemChanged = {
                            detailViewModel.updateDropDownSelectedMin(it.toInt())
                        },
                        label = TimePart.Minute.toString()  // { androidx.compose.material.Text("Minute") }
                    )                       /** Minute TimeCard */
                }   /** Hour and Minute TimeCards */

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
}       // End of TimePicker()

//@Preview
//@Composable
//fun showPicker() {
//    TimePicker(
//        onCancel = { Log.i("TimePicker","Preview Cancel") },
//        onConfirm = { Log.i("TimePicker","Preview Confirm") },
//        toggle = { Log.i("TimePicker","Preview toggle") },
//        content = { Log.i("TimePicker","Preview content") }
//    )
//}