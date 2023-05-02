package com.enescanpolat.artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.enescanpolat.artbooktesting.adapter.artRecyclerAdapter
import com.enescanpolat.artbooktesting.adapter.imageRecyclerAdapter
import javax.inject.Inject

class artFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: artRecyclerAdapter ,
    private val glide : RequestManager,
    private val imageRecyclerAdapter: imageRecyclerAdapter,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            artFragment::class.java.name -> artFragment(artRecyclerAdapter)
            imageapiFragment::class.java.name -> imageapiFragment(imageRecyclerAdapter)
            artDetailsFragment::class.java.name -> artDetailsFragment(glide)
            else->super.instantiate(classLoader, className)
        }


    }
}