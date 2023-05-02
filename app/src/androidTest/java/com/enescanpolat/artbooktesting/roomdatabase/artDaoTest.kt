package com.enescanpolat.artbooktesting.roomdatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.enescanpolat.artbooktesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class artDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
     var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testdatabase")
    lateinit var database: artDatabase

    private lateinit var dao :artDao


    @Before
    fun setup(){

        /*
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),artDatabase::class.java
        ).allowMainThreadQueries().build()

         */

        hiltRule.inject()

        dao = database.artDao()


    }

    @After
    fun teardown(){
        database.close()

    }

    @Test
    fun insertArtTesting()= runBlocking{
        val exampleart = art("monlisa","vinci",1700,"test.com",1)
        dao.insertArt(exampleart)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleart)


    }

    @Test
    fun deleteArtTesting()= runBlocking{
        val exampleart = art("monlisa","vinci",1700,"test.com",1)
        dao.insertArt(exampleart)
        dao.deleteArt(exampleart)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleart)



    }


}