package com.enescanpolat.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.getOrAwaitValue
import com.enescanpolat.artbooktesting.launchFragmentInHiltContainer
import com.enescanpolat.artbooktesting.repo.fakeartRepositoryTest
import com.enescanpolat.artbooktesting.roomdatabase.art
import com.enescanpolat.artbooktesting.viewmodel.artViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class artDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentfactory : artFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testandroidDetailsFragment(){

        val navcontroller = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<artDetailsFragment>(factory = fragmentfactory){

            Navigation.setViewNavController(requireView(),navcontroller)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artimageView)).perform(click())

        Mockito.verify(navcontroller).navigate(artFragmentDirections.actionArtFragmentToArtDetailsFragment())


    }

    @Test
    fun onbackpressed(){
        val navcontroller = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<artDetailsFragment>(factory = fragmentfactory){

            Navigation.setViewNavController(requireView(),navcontroller)
        }

        Espresso.pressBack()
        Mockito.verify(navcontroller).popBackStack()

    }

    @Test
    fun testSave(){

        val testviewmodel = artViewModel(fakeartRepositoryTest())
        launchFragmentInHiltContainer<artDetailsFragment>(factory = fragmentfactory){
            viewModel = testviewmodel
        }

        Espresso.onView(withId(R.id.nametext)).perform(replaceText("monalisa"))
        Espresso.onView(withId(R.id.artisttext)).perform(replaceText("vinci"))
        Espresso.onView(withId(R.id.yeartext)).perform(replaceText("1700"))
        Espresso.onView(withId(R.id.savebutton)).perform(click())

        assertThat(testviewmodel.artlist.getOrAwaitValue()).contains(
            art("monalisa","vinci",1700,"")
        )

    }


}