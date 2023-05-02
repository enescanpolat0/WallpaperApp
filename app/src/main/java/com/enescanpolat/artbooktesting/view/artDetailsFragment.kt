package com.enescanpolat.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.databinding.FragmetArtDetailsBinding
import com.enescanpolat.artbooktesting.util.Status
import com.enescanpolat.artbooktesting.viewmodel.artViewModel
import javax.inject.Inject

class artDetailsFragment @Inject constructor(val glide : RequestManager):Fragment(R.layout.fragmet_art_details) {

    private var fragmentBinding : FragmetArtDetailsBinding?=null
    lateinit var viewModel: artViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(artViewModel::class.java)

        val binding=FragmetArtDetailsBinding.bind(view)
        fragmentBinding=binding
        subscribetoObservers()

        binding.artimageView.setOnClickListener {
            findNavController().navigate(artDetailsFragmentDirections.actionArtDetailsFragmentToImageapiFragment())
        }

        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                findNavController().popBackStack()

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.savebutton.setOnClickListener {
            viewModel.makeArt(binding.nametext.text.toString(),binding.artisttext.text.toString(),binding.yeartext.text.toString())

        }

    }

    private fun subscribetoObservers(){
        viewModel.selectedUrl.observe(viewLifecycleOwner, Observer {url->
            fragmentBinding?.let {
                glide.load(url).into(it.artimageView)
            }
        })

        viewModel.insertartmessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                   Status.SUCCESS->{
                       Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                       findNavController().popBackStack()
                       viewModel.resetInsertArtMsg()
                   }
                   Status.ERROR->{
                       Toast.makeText(requireContext(),it.message ?:"Error",Toast.LENGTH_LONG).show()

                   }
                   Status.LOADING->{

                   }

            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()

    }

}