package com.example.banaoassignment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banaoassignment.adapters.ImageAdapter
import com.example.banaoassignment.data.models.Photo
import com.example.banaoassignment.databinding.ItemImagePreviewBinding

class ImagePagingAdapter: PagingDataAdapter<Photo, ImagePagingAdapter.ImageViewHolder>(diffCallback = differCallBack ) {
     class ImageViewHolder(val binding: ItemImagePreviewBinding):RecyclerView.ViewHolder(binding.root)



   companion object {
       private val differCallBack = object : DiffUtil.ItemCallback<Photo>() {
           override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
               return oldItem.id == newItem.id
           }

           override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
               return newItem.id == oldItem.id
           }
       }
   }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val curritem = getItem(position)

        holder.binding.apply {
            if(curritem != null){
                Glide.with(root.context).load(curritem.url_s).into(ivImages)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemImagePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}