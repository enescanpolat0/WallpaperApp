package com.enescanpolat.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.enescanpolat.artbooktesting.roomdatabase.artDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object testappmodule {

    @Provides
    @Named("testdatabase")
    fun injectMemoryRoom(@ApplicationContext context:Context)=
        Room.inMemoryDatabaseBuilder(context,artDatabase::class.java)
            .allowMainThreadQueries()
            .build()




}