package com.enescanpolat.artbooktesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.artbooktesting.model.imageResponse
import com.enescanpolat.artbooktesting.repo.artRepository
import com.enescanpolat.artbooktesting.repo.artRepositoryInterface
import com.enescanpolat.artbooktesting.roomdatabase.art
import com.enescanpolat.artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class artViewModel @Inject constructor(
    private val repository: artRepositoryInterface
)  :ViewModel() {

    //art fragment

    val artlist = repository.getArt()

    //ımage api fragment

    private val images = MutableLiveData <Resource<imageResponse>>()
    val imageList : LiveData<Resource<imageResponse>>
        get() = images
    private val selectedimage = MutableLiveData<String>()
    val selectedUrl : LiveData<String>
        get() = selectedimage

    //art details
    private var insertartMsg = MutableLiveData<Resource<art>>()
    val insertartmessage : LiveData<Resource<art>>
        get() = insertartMsg


    fun resetInsertArtMsg(){
        insertartMsg=MutableLiveData<Resource<art>>()
    }




    fun setSelectedImage(url:String){
        selectedimage.postValue(url)
    }

    fun deleteArt(art: art) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt(art: art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String , artistname : String , year : String){
        if (name.isEmpty()||artistname.isEmpty()||year.isEmpty()){
            insertartMsg.postValue(Resource.error("Enter name , artistname , year",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertartMsg.postValue(Resource.error("Yılı giriniz",null))
            return
        }

        val art = art(name,artistname,yearInt,selectedimage.value?:"")
        insertArt(art)
        setSelectedImage("")
        insertartMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String){
        if (searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value=response
        }
    }

}