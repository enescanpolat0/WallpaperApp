package com.enescanpolat.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.adapter.imageRecyclerAdapter
import com.enescanpolat.artbooktesting.getOrAwaitValue
import com.enescanpolat.artbooktesting.launchFragmentInHiltContainer
import com.enescanpolat.artbooktesting.repo.fakeartRepositoryTest
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
class imageAPIfragmentTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : artFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedimageUrl = "test.com"
        val testViewmodel = artViewModel(fakeartRepositoryTest())

        launchFragmentInHiltContainer<imageapiFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel=testViewmodel
            imageRecyclerAdapter.images= listOf(selectedimageUrl)
        }

        Espresso.onView(withId(R.id.image_RecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<imageRecyclerAdapter.imageViewHolder>(0,click())
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewmodel.selectedUrl.getOrAwaitValue()).isEqualTo(selectedimageUrl)

    }

}