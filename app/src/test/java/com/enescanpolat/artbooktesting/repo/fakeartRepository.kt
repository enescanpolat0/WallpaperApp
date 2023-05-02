package com.enescanpolat.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enescanpolat.artbooktesting.model.imageResponse
import com.enescanpolat.artbooktesting.roomdatabase.art
import com.enescanpolat.artbooktesting.util.Resource

class fakeartRepository:artRepositoryInterface {

    private val arts = mutableListOf<art>()
    private val artsLivedata = MutableLiveData<List<art>>(arts)

    override suspend fun insertArt(art: art) {
        arts.add(art)
        refreshdata()
    }

    override suspend fun deleteArt(art: art) {
        arts.remove(art)
        refreshdata()
    }

    override fun getArt(): LiveData<List<art>> {
        return artsLivedata
    }

    override suspend fun searchImage(imageString: String): Resource<imageResponse> {
        return Resource.success(imageResponse(listOf(),0,0))
    }

    private fun refreshdata(){
        artsLivedata.postValue(arts)
    }

}