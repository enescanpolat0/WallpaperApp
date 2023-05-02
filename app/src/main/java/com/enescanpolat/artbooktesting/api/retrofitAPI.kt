package com.enescanpolat.artbooktesting.api

import com.enescanpolat.artbooktesting.model.imageResponse
import com.enescanpolat.artbooktesting.util.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") seachQuery : String,
        @Query("key") apiKey : String = API_KEY
    ) : Response<imageResponse>
}