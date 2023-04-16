package com.rayko.maintcall.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Delete : BottomBar(
        "delete",
        "Delete",
        icon = Icons.Default.Delete
    )
    object Save : BottomBar(
        "save",
        "Save",
        icon = Icons.Default.Home
    )
    object Cancel : BottomBar(
        "cancel",
        "Cancel",
        icon = Icons.Default.Person
    )
}
