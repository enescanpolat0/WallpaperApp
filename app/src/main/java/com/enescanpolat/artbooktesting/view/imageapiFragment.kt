package com.enescanpolat.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.adapter.imageRecyclerAdapter
import com.enescanpolat.artbooktesting.databinding.FragmentImageApiBinding
import com.enescanpolat.artbooktesting.util.Status
import com.enescanpolat.artbooktesting.viewmodel.artViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class imageapiFragment
    @Inject constructor(val imageRecyclerAdapter : imageRecyclerAdapter)
    :Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: artViewModel
    private var fragmentbinding : FragmentImageApiBinding?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(artViewModel::class.java)
        subscribetoObservers()



        val binding = FragmentImageApiBinding.bind(view)
        fragmentbinding=binding

        var job :Job?=null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }

        }

        binding.imageRecyclerView.adapter=imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribetoObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    val urls = it.data?.hits?.map { imageResult ->
                         imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls?: listOf()

                    fragmentbinding?.progresBar?.visibility=View.GONE
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_LONG).show()
                    fragmentbinding?.progresBar?.visibility=View.GONE

                }
                Status.LOADING->{
                    fragmentbinding?.progresBar?.visibility=View.VISIBLE

                }


            }
        })
    }
}