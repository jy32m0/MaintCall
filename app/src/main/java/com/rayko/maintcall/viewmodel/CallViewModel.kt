package com.rayko.maintcall.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.rayko.maintcall.data.CallEntity
import com.rayko.maintcall.data.CallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// to make use of CallRepository and provide the information
// from the repository to the UI.

interface CallViewModelAbstract {
    val callListFlow: Flow<List<CallEntity>>
    fun getLastCall(): Flow<CallEntity?>
    fun getCall(id: Long): Flow<CallEntity?>
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
//    val callById: Flow<CallEntity?> = callRepository.getCall(0)

    override val callListFlow: Flow<List<CallEntity>>
        get() = callRepository.getAllFlow()

    override fun getLastCall(): Flow<CallEntity?>
        = callRepository.getLastCall()

    override fun getCall(id: Long): Flow<CallEntity?>
        = callRepository.getCall(id)

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