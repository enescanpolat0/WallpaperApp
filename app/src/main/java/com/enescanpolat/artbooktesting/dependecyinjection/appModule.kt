package com.enescanpolat.artbooktesting.dependecyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.api.retrofitAPI
import com.enescanpolat.artbooktesting.repo.artRepository
import com.enescanpolat.artbooktesting.repo.artRepositoryInterface
import com.enescanpolat.artbooktesting.roomdatabase.artDao
import com.enescanpolat.artbooktesting.roomdatabase.artDatabase
import com.enescanpolat.artbooktesting.util.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object appModule {

    @Singleton
    @Provides
    fun injectRoomDatabase
                (@ApplicationContext context: Context) = Room.databaseBuilder(
        context ,artDatabase::class.java,"ArtBookDB"
        ).build()


    @Singleton
    @Provides
    fun injectDao(database: artDatabase) = database.artDao()



    @Singleton
    @Provides
    fun injectRetrofitAPI() : retrofitAPI {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(retrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao:artDao , api : retrofitAPI) = artRepository(dao,api) as artRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )


}