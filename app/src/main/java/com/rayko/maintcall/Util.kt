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

//fun hourMinToMilli(hh: Int, mm: Int): Long {
//
//}

@Composable
fun calledDate(time: Long): String {
    return  convertTime(timeCalled = time, form = "dateCalled")
}

@Composable
fun calledTime(time: Long): String {
    val called = stringResource(id = R.string.called)
    return "$called ${convertTime(timeCalled = time, form = "timeCalled")}"
}

@Composable
fun clearedTime(time: Long): String {
    val cleared = stringResource(id = R.string.cleared)
    return "$cleared ${convertTime(timeCleared = time, form = "timeCleared")}"
}

@Composable
fun downedTime(time: Long): String {
    val downed = stringResource(id = R.string.downed)
    return "$downed ${convertTime(timeDowned = time / 1000, form = "diff")}"
}

fun convertTime(
    timeCalled: Long = 0L,
    timeCleared: Long = 0L,
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
            "$hour Hour $min Minutes"
        }
        else -> "Time input ERROR"
    }

    return converted
}

//************************* VV USED IN TimePicker VV **************************

@Composable
fun Clock(
    time: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var radiusPx by remember { mutableStateOf(0) }
    var radiusInsidePx by remember { mutableStateOf(0) }

    fun posX(index: Int) =
        ((if (index < 12) radiusPx else radiusInsidePx) * cos(angleForIndex(index))).toInt()

    fun posY(index: Int) =
        ((if (index < 12) radiusPx else radiusInsidePx) * sin(angleForIndex(index))).toInt()

    Box(modifier = modifier) {
        Surface(
            color = primaryColor,
            shape = CircleShape,
            modifier = Modifier.fillMaxSize()
        ) {}

        val padding = 4.dp
        val hourCirclePx = 36f

        Layout(
            content = content,
            modifier = Modifier
                .padding(padding)
                .drawBehind {
                    val end = Offset(
                        x = size.width / 2 + posX(time),
                        y = size.height / 2 + posY(time)
                    )

                    drawCircle(
                        radius = 9f,
                        color = selectedColor,
                    )

                    drawLine(
                        start = center,
                        end = end,
                        color = selectedColor,
                        strokeWidth = 4f
                    )

                    drawCircle(
                        radius = hourCirclePx,
                        center = end,
                        color = selectedColor,
                    )
                }
        ) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            assert(placeables.count() == 12 || placeables.count() == 24) { "Missing items: should be 12 or 24, is ${placeables.count()}" }

            layout(constraints.maxWidth, constraints.maxHeight) {
                val size = constraints.maxWidth
                val maxSize = maxOf(placeables.maxOf { it.width }, placeables.maxOf { it.height })

                radiusPx = (constraints.maxWidth - maxSize) / 2
                radiusInsidePx = (radiusPx * 0.67).toInt()

                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        size / 2 - placeable.width / 2 + posX(index),
                        size / 2 - placeable.height / 2 + posY(index),
                    )
                }
            }
        }
    }
}

@Composable
fun Mark(
    text: String,
    index: Int, // 0..23
    onIndex: (Int) -> Unit,
    isSelected: Boolean
) {
    Text(
        text = text,
        color = if (isSelected) secondaryColor else Color.White,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onIndex(index) }
        )
    )
}

@Composable
fun TimeCard(
    time: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        backgroundColor = if (isSelected) selectedColor else secondaryColor,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = String.format("%02d", time),  //time.toString(),
            fontSize = 26.sp,
            color = if (isSelected) secondaryColor else Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

enum class TimePart { Hour, Minute }

private const val step = PI * 2 / 12
private fun angleForIndex(hour: Int) = -PI / 2 + step * hour

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(
    detailViewModel: DetailViewModel = viewModel(),
    items: List<Int>,
    selectedItem: String,
    selectedItemChanged: (String) -> Unit,
    label: String   //@Composable () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
//    var selectedNumber = detailViewModel.dropDownSelected
//    var selectedOptionText by rememberSaveable { mutableStateOf("") }

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
@Composable
fun ClockMarksRoman(selectedPart: TimePart, selectedTime: Int, onTime: (Int) -> Unit) {
    if (selectedPart == TimePart.Hour) {
        Mark(text = "XII", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "I", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "II", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "III", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "IV", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "V", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "VI", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "VII", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "VIII", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "IX", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "X", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "XI", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
        Mark(text = "XII", index = 12, isSelected = selectedTime == 12, onIndex = onTime)
        Mark(text = "XIII", index = 13, isSelected = selectedTime == 13, onIndex = onTime)
        Mark(text = "XIV", index = 14, isSelected = selectedTime == 14, onIndex = onTime)
        Mark(text = "XV", index = 15, isSelected = selectedTime == 15, onIndex = onTime)
        Mark(text = "XVI", index = 16, isSelected = selectedTime == 16, onIndex = onTime)
        Mark(text = "XVII", index = 17, isSelected = selectedTime == 17, onIndex = onTime)
        Mark(text = "XVIII", index = 18, isSelected = selectedTime == 18, onIndex = onTime)
        Mark(text = "XIX", index = 19, isSelected = selectedTime == 19, onIndex = onTime)
        Mark(text = "XX", index = 20, isSelected = selectedTime == 20, onIndex = onTime)
        Mark(text = "XXI", index = 21, isSelected = selectedTime == 21, onIndex = onTime)
        Mark(text = "XXII", index = 22, isSelected = selectedTime == 22, onIndex = onTime)
        Mark(text = "XXIII", index = 23, isSelected = selectedTime == 23, onIndex = onTime)
    } else {
        Mark(text = "XII", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "I", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "II", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "III", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "IV", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "V", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "VI", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "VII", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "VIII", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "IX", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "X", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "XI", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
    }
}

//************************* ^^ USED IN TimePicker ^^ **************************


@Preview(device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun TimerPickerPreview() {
    var pickedHour by rememberSaveable { mutableStateOf(timeNow(TimePart.Hour)) }
    ExposedDropdownMenu(
        items = (1..23).toList(),
        selectedItem = pickedHour.toString(),  //selectedNumber,
        selectedItemChanged = { index ->
            pickedHour = index.toInt()
        },
        label ="Hour"
    )
}

