package com.rayko.maintcall.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call")
data class CallEntity(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0L,

    @ColumnInfo(name = "equip_type")
    var equipType: String = "",

    @ColumnInfo(name = "equip_num")
    var equipNum: Int = 1,

    @ColumnInfo(name = "call_time")
    var callTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "clear_time")
    var clearTime: Long = callTime,

    @ColumnInfo(name = "down_time")
    var downTime: Long = 0L,

    @ColumnInfo(name = "call_reason")
    var callReason: String? = "",

    @ColumnInfo(name = "clear_solution")
    var clearSolution: String? = ""
)