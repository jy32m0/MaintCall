package com.rayko.maintcall

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Window
//import androidx.compose.ui.window.application
//
//fun main() = application {
//    Window {
//        MyApp()
//    }
//}

@Composable
fun MyApp() {
    var selectedHour by remember { mutableStateOf("") }
    var selectedMinute by remember { mutableStateOf("") }

    Row {
        ExposedDropdownMenu(
            items = (1..23).toList(),
            selectedItem = selectedHour,
            selectedItemChanged = { index ->
                selectedHour = index
            },
            label = "Hour"
        )

        Spacer(modifier = Modifier.width(16.dp))

        ExposedDropdownMenu(
            items = (1..59).toList(),
            selectedItem = selectedMinute,
            selectedItemChanged = { index ->
                selectedMinute = index
            },
            label = "Minute"
        )
    }
}

//@Composable
//fun ExposedDropdownMenu(
//    items: List<Int>,
//    selectedItem: Int,
//    onItemSelected: (Int) -> Unit,
//    label: @Composable () -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = selectedItem.toString(),
//        onValueChange = {},
//        readOnly = true,
//        label = label,
//        trailingIcon = {
//            Icon(
//                imageVector = Icons.Filled.ArrowDropDown,
//                contentDescription = null
//            )
//        },
//        modifier = Modifier
//            .width(120.dp)
//            .clickable { expanded = true }
//    )
//
//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false }
//    ) {
//        items.forEach { item ->
//            DropdownMenuItem(
//                onClick = {
//                    onItemSelected(item)
//                    expanded = false
//                }
//            ) {
//                Text(item.toString())
//            }
//        }
//    }
//}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp()
}
