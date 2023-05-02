package com.enescanpolat.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.roomdatabase.art
import javax.inject.Inject

class artRecyclerAdapter@Inject constructor(val  glide : RequestManager):RecyclerView.Adapter<artRecyclerAdapter.artViewHolder>() {


    class artViewHolder(itemview : View):RecyclerView.ViewHolder(itemview){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<art>(){
        override fun areItemsTheSame(oldItem: art, newItem: art): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: art, newItem: art): Boolean {
            return  oldItem==newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var arts:List<art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): artViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return artViewHolder(view)
    }

    override fun onBindViewHolder(holder: artViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.art_Row_imageview)
        val nameText = holder.itemView.findViewById<TextView>(R.id.art_Row_nametext)
        val artistnameText = holder.itemView.findViewById<TextView>(R.id.art_Row_artistnametext)
        val yearText = holder.itemView.findViewById<TextView>(R.id.art_Row_yeartext)
        val art = arts[position]
        holder.itemView.apply {
            nameText.text="Name = ${art.name}"
            artistnameText.text="Artistname = ${art.artistname}"
            yearText.text="Year = ${art.year}"
            glide.load(art.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
       return arts.size
    }
}