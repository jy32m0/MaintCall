package com.rayko.maintcall

//import androidx.compose.material.Text
import android.annotation.SuppressLint
import androidx.compose.material3.Text
//import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material.TextField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rayko.maintcall.viewmodel.DetailViewModel


private val primaryColor = Color(68, 71, 70)
private val secondaryColor = Color(68, 71, 70)
private val selectedColor = Color(104, 220, 255, 255)

fun toDay(): String {
    return convertTime(System.currentTimeMillis(), form = "dateCalled")
}

@SuppressLint("SimpleDateFormat")
fun timeNow(u: TimePart): Int {
    val formatHour = SimpleDateFormat("HH")
    val formatMinute = SimpleDateFormat("mm")
    return when (u) {
        TimePart.Hour -> formatHour.format(System.currentTimeMillis()).toInt()
        TimePart.Minute -> formatMinute.format(System.currentTimeMillis()).toInt()
        else -> 0
    }
}


@Composable
fun calledDate(time: Long): String {
    return  convertTime(timeCalled = time, form = "dateCalled")
}

@Composable
fun calledTime(time: Long?): String {
    val called = stringResource(id = R.string.called)
    return "$called ${convertTime(timeCalled = time, form = "timeCalled")}"
}

@Composable
fun clearedTime(time: Long?): String {
    val cleared = stringResource(id = R.string.cleared)
    return "$cleared ${convertTime(timeCleared = time, form = "timeCleared")}"
}

fun timeNowMilli() {
    System.currentTimeMillis()
}

@Composable
fun downedTime(time: Long?): String {
    var toReturn = ""
    val downed = stringResource(id = R.string.downed)
    if (time != null) {
        toReturn = "$downed ${convertTime(timeDowned = time / 1000, form = "diff")}"
    }
    return toReturn
}

fun convertTime(
    timeCalled: Long? = 0L,
    timeCleared: Long? = 0L,
    timeDowned: Long = 0L,
    form: String
): String {
    val dateFormat = SimpleDateFormat("MM/ dd/ yyyy")
    val timeFormat = SimpleDateFormat("HH:mm:ss")
    var converted = "??"
    val hour = floor(timeDowned / 3600.00).toInt()
    val min = floor((timeDowned % 3600.00) / 60.00).toInt()

    converted = when (form) {
        "dateCalled" -> dateFormat.format(timeCalled)
        "timeCalled" -> timeFormat.format(timeCalled)
        "timeCleared" -> timeFormat.format(timeCleared)
        "diff" -> if (hour < 1) {
            "$min Minutes"
        } else {
            "$hour Hours $min Minutes"
        }
        else -> "Time input ERROR"
    }

    return converted
}

//************************* VV USED IN TimePicker VV **************************


enum class TimePart { Hour, Minute }

private const val step = PI * 2 / 12
private fun angleForIndex(hour: Int) = -PI / 2 + step * hour

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(
    detailViewModel: DetailViewModel,// = viewModel(),
    items: List<Int>,
    selectedItem: String,
    selectedItemChanged: (String) -> Unit,
    label: String   //@Composable () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .width(90.dp),
            value = selectedItem.toString(),
            onValueChange = { selectedItemChanged },
            readOnly = true,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.toString()) },
                    onClick = {
                        if (label == TimePart.Hour.toString()) {
                            detailViewModel.updateDropDownSelectedHour(selectionOption)
                        } else {
                            detailViewModel.updateDropDownSelectedMin(selectionOption)
                        }
                        expanded = false
                    },
//                    contentPadding = ExposedDropdownMenuDefaults.DropdownMenuItemContentPadding,
                )
            }
        }
    }
}


