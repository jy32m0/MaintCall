package com.rayko.maintcall.viewmodel

import androidx.lifecycle.ViewModel
import com.rayko.maintcall.data.CallEntity
import com.rayko.maintcall.data.CallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

// to make use of CallRepository and provide the information
// from the repository to the UI.

interface CallViewModelAbstract {
    val callListFlow: Flow<List<CallEntity>>
    fun getLastCall(call: CallEntity)
    fun getCall(id: Long)
    fun insertCall(call: CallEntity)
    fun updateCall(call: CallEntity)
    fun deleteCall(call: CallEntity)
    fun deleteAllCalls()
}

@HiltViewModel
class CallViewModel
@Inject
constructor(
    private val callRepository: CallRepository
) : ViewModel(), CallViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override val callListFlow: Flow<List<CallEntity>>
        get() = callRepository.getAllFlow()

    override fun getLastCall(call: CallEntity) {
        ioScope.launch {
            callRepository.getLastCall()
        }
    }

    override fun getCall(id: Long) {
        ioScope.launch {
            callRepository.getCall(id = id)
        }
    }

    override fun insertCall(call: CallEntity) {
        ioScope.launch {
            callRepository.insert(call = call)
        }
    }

    override fun updateCall(call: CallEntity) {
        ioScope.launch {
            callRepository.update(call = call)
        }
    }

    override fun deleteCall(call: CallEntity) {
        ioScope.launch {
            callRepository.delete(call = call)
        }
    }

    override fun deleteAllCalls() {
        ioScope.launch {
            callRepository.deleteAll()
        }
    }
}