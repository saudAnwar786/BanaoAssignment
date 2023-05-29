package com.example.banaoassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banaoassignment.databinding.ItemImagePreviewBinding
import com.example.banaoassignment.models.Photo
import com.example.banaoassignment.models.Photos

class ImageAdapter:RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(var binding:ItemImagePreviewBinding):RecyclerView.ViewHolder(binding.root)

    val differCallBack = object : DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemImagePreviewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currItem= differ.currentList[position]
        holder.binding.apply {
            val context  = root.context
            Glide.with(context).load(currItem.url_s)
                .centerCrop()
                .into(ivImages)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}