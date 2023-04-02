package com.rayko.maintcall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CallEntity::class], version = 3)
abstract class CallDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao

    companion object {
        private var INSTANCE: CallDatabase? = null

        fun getInstance(context: Context): CallDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, CallDatabase::class.java, "call.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as CallDatabase
        }
    }
}