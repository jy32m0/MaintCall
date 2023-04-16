package com.rayko.maintcall.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CallDao {

    @Query("SELECT * FROM call ORDER BY roomId DESC") 
    fun getAllFlow(): Flow<List<CallEntity>>

    @Query("SELECT * FROM call ORDER BY roomId DESC LIMIT 1")
    fun getLastCall(): Flow<CallEntity?>

    @Query("SELECT * FROM call WHERE roomId = :id")
    fun getCall(id: Long): Flow<CallEntity?>

    @Insert
    suspend fun insert(call: CallEntity)

    @Update
    suspend fun update(call: CallEntity)

    @Delete
    suspend fun delete(call: CallEntity)

    @Query("DELETE FROM call")
    suspend fun deleteAll()
}