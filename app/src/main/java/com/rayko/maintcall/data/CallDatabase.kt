package com.rayko.maintcall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CallEntity::class], version = 3)
abstract class CallDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao     // Now, database knows this DAO

    companion object {      // allows access to the methods to create/get database
                            // and uses the class name as qualifier.

        @Volatile           // no cache, but read/write from main memory.
        private var INSTANCE: CallDatabase? = null

        fun getInstance(context: Context): CallDatabase {
            return INSTANCE ?: synchronized(this) {     // if INSTANCE is null
                Room
                    .databaseBuilder(
                        context,
                        CallDatabase::class.java,
                        "call.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }     // refer Instance to the recently created DB instance
            }
//            synchronized(this) {        // single thread only
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context,
//                        CallDatabase::class.java,
//                        "call.db"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance // INSTANCE as CallDatabase
//            }
        }
    }
}