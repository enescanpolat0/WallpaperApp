package com.enescanpolat.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.enescanpolat.artbooktesting.model.imageResponse
import com.enescanpolat.artbooktesting.roomdatabase.art
import com.enescanpolat.artbooktesting.util.Resource

interface artRepositoryInterface {

    suspend fun insertArt(art: art)

    suspend fun deleteArt(art: art)

    fun getArt() : LiveData<List<art>>

    suspend fun searchImage(imageString: String) : Resource<imageResponse>

}