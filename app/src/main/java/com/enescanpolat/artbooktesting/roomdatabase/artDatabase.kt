package com.enescanpolat.artbooktesting.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [art::class], version = 1)
abstract class artDatabase :RoomDatabase() {

    abstract fun artDao() : artDao


}