package com.example.airlift.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.airlift.R
import com.example.airlift.databinding.HolderMovieBinding
import com.example.airlift.datamodel.main.ImageDomainModel

//
// Created by Abdul Basit on 11/4/2020.
//


//using diff utils for better optimization
class ImagesAdapter internal constructor(
    private val callback: (imageUrl: String) -> Unit
) : PagingDataAdapter<ImageDomainModel, RecyclerView.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<HolderMovieBinding>(
            LayoutInflater.from(parent.context),
            R.layout.holder_movie,
            parent,
            false
        )
        return ImagesHolder(binding)
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { callback.invoke(item!!.largeImageUrl) }
        (holder as ImagesHolder).bind(item!!)
    }

    inner class ImagesHolder internal constructor(private val binding: HolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ImageDomainModel) {
            binding.model = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ImageDomainModel
            >() {
        override fun areItemsTheSame(
            oldItem: ImageDomainModel,
            newItem: ImageDomainModel

        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ImageDomainModel,
            newItem: ImageDomainModel

        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
