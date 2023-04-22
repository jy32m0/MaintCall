package com.rayko.maintcall.ui.screens

import android.app.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DialogDelete(
    // refer to AlertViewModel
    onConfirm:()->Unit,
    onDismiss:()->Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Delete?")
        },
        text = {
            Text("The selected record will be permanently DELETED.")
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() }
            ) {
                Text(
                    text = "DELETE",
                    color = Color.White
                )
            }
            Button(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White)
            }
        }
    )
}

@Preview
@Composable
fun prvDialog() {
    DialogDelete(
        onConfirm = {},
        onDismiss = { /*TODO*/ }
    )
}