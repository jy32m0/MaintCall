package com.rayko.maintcall.data

import kotlinx.coroutines.flow.Flow

// to use the DAO and to manage the objects inside the database
class CallRepository (
    private val callDao: CallDao
) {
    fun getAllFlow(): Flow<List<CallEntity>> = callDao.getAllFlow()

    fun getLastCall(): Flow<CallEntity?> = callDao.getLastCall()

    fun getCall(id: Long): Flow<CallEntity?> =
        callDao.getCall(id = id)

    suspend fun insert(call: CallEntity) = callDao.insert(call = call)

    suspend fun update(call: CallEntity?) = callDao.update(call = call)

    suspend fun delete(call: CallEntity?) = callDao.delete(call = call)

    suspend fun deleteAll() = callDao.deleteAll()
}