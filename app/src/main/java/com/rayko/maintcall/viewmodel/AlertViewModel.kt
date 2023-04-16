package com.rayko.maintcall.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AlertViewModel : ViewModel() {

    var showAlert by mutableStateOf(false)
        private set

    fun onDelete() {
        showAlert = true
    }

    fun onDismissAlert() {
        showAlert = false
    }
}