package com.rayko.maintcall.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class AlertViewModel(
    private val callViewModel: CallViewModelAbstract,
) : ViewModel() {

    var showDelete by mutableStateOf(false)
        private set
    fun onDelete() { showDelete = true }
    fun onDismissDelete() { showDelete = false }

    var showDropDown by mutableStateOf(false)
    fun onDropDown() { showDropDown = true }
    fun onDismissDropDown() { showDropDown = false }

    var confirmDelete by mutableStateOf(false)

    @Composable
    fun ConfirmDeleteRec(id: String) {
        val thisCall by callViewModel.getCall(id.toLong())
            .collectAsState(initial = null)
        thisCall?.let {
            callViewModel.deleteCall(it)
        }
        confirmDelete = false
        onDismissDelete()
    }
}