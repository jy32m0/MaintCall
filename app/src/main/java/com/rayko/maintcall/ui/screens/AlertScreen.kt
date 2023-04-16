package com.rayko.maintcall.ui.screens

import android.app.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DialogDelete(
    onDismiss:()->Unit,
    onConfirm:()->Unit
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
                Text(text = "DELETE")
            }
            Button(
                onClick = { onDismiss() }
            ) {
                Text(text = "Cancel")    
            }
        }
    )
}

@Preview
@Composable
fun prvDialog() {
    DialogDelete(onDismiss = { /*TODO*/ }) {
        
    }
}