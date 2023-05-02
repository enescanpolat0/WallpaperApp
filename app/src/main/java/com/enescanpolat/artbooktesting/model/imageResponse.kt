package com.enescanpolat.artbooktesting.model

data class imageResponse(

    val hits : List<imageResult>,
    val total : Int ,
    val totalHits : Int

)
