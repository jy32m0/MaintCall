package com.rayko.maintcall

import android.app.Application
import com.rayko.maintcall.data.CallDao
import com.rayko.maintcall.data.CallDatabase
import com.rayko.maintcall.data.CallRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// to tell Hilt how to provide the repository object, CallDatabase, and CallDao

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Singleton
    @Provides
    fun provideCallRepository(callDao: CallDao): CallRepository {
        return CallRepository(callDao = callDao)
    }

    @Singleton
    @Provides
    fun provideCallDatabase(call: Application): CallDatabase {
        return CallDatabase.getInstance(context = call)
    }

    @Singleton
    @Provides
    fun provideCallDao(callDatabase: CallDatabase): CallDao {
        return callDatabase.callDao()
    }
}