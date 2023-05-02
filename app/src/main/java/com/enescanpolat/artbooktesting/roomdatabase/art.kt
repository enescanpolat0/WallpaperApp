package com.enescanpolat.artbooktesting.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("arts")
data class art (

    var name :String ,
    var artistname : String ,
    var year : Int ,
    var imageUrl : String,

    @PrimaryKey(true)
    var id : Int? = null




){
}