package com.rayko.maintcall.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class AlertViewModel : ViewModel() {

    var showDelete by mutableStateOf(false)
        private set
    fun onDelete() { showDelete = true }
    fun onDismissDelete() { showDelete = false }

    var showDropDown by mutableStateOf(false)
    fun onDropDown() { showDropDown = true }
    fun onDismissDropDown() { showDropDown = false }
}