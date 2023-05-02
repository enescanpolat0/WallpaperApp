package com.enescanpolat.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.adapter.artRecyclerAdapter
import com.enescanpolat.artbooktesting.databinding.FragmentArtsBinding
import com.enescanpolat.artbooktesting.viewmodel.artViewModel
import javax.inject.Inject

class artFragment
    @Inject constructor(val artRecyclerAdapter : artRecyclerAdapter)
    :Fragment(R.layout.fragment_arts) {

    private var fragmentbinding: FragmentArtsBinding?=null
    lateinit var viewModel: artViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(artViewModel::class.java)

        val binding=FragmentArtsBinding.bind(view)
        fragmentbinding=binding

        subscribetoObservers()
        binding.recyclerViewArts.adapter = artRecyclerAdapter
        binding.recyclerViewArts.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArts)

        binding.fab.setOnClickListener {
            findNavController().navigate(artFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

    }

    private fun subscribetoObservers(){
        viewModel.artlist.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {

        fragmentbinding=null
        super.onDestroyView()
    }

}