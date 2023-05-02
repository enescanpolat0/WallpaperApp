package com.enescanpolat.artbooktesting.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface artDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art:art)

    @Delete
    suspend fun deleteArt(art: art)


    @Query("SELECT * FROM arts")
    fun observeArts():LiveData<List<art>>


}