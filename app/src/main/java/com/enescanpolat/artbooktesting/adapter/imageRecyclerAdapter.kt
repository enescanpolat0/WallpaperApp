package com.enescanpolat.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enescanpolat.artbooktesting.R
import com.enescanpolat.artbooktesting.roomdatabase.art
import javax.inject.Inject

class imageRecyclerAdapter@Inject constructor(val glide : RequestManager):RecyclerView.Adapter<imageRecyclerAdapter.imageViewHolder>() {

    class imageViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)

    private var onItemClickListener : ((String)->Unit)?=null



    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem==newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images:List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return imageViewHolder(view)
    }

    fun setOnItemClickListener(listener : (String) -> Unit ){
        onItemClickListener=listener
    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.singleArtimageView)
        val url = images[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }


}