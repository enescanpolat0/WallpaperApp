package com.enescanpolat.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enescanpolat.artbooktesting.MainCoroutineRule
import com.enescanpolat.artbooktesting.getOrAwaitValueTest
import com.enescanpolat.artbooktesting.repo.fakeartRepository
import com.enescanpolat.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class artViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: artViewModel


    @Before
    fun setup(){
        //TEST DOUBLES -> kopyasını test et kendisini test etme


        viewModel=artViewModel(fakeartRepository())

    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("mon","vinci","")
        val value = viewModel.insertartmessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)


    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","vinci","1234")
        val value = viewModel.insertartmessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without artistname returns error`(){
        viewModel.makeArt("mon","","1235")
        val value = viewModel.insertartmessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
}