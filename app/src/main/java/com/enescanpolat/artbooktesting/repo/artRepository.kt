package com.enescanpolat.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.enescanpolat.artbooktesting.api.retrofitAPI
import com.enescanpolat.artbooktesting.model.imageResponse
import com.enescanpolat.artbooktesting.roomdatabase.art
import com.enescanpolat.artbooktesting.roomdatabase.artDao
import com.enescanpolat.artbooktesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class artRepository @Inject constructor(private val artDao: artDao , private val retrofitAPI: retrofitAPI) : artRepositoryInterface {
    override suspend fun insertArt(art: art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<imageResponse> {
        return  try {

            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error" , null)
            }

        }catch (e:Exception){
            Resource.error("NO DATA ",null)
        }
    }
}