package com.example.banaoassignment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.banaoassignment.databinding.ItemLoaderBinding

class LoaderPaginAdapter:LoadStateAdapter<LoaderPaginAdapter.LoaderViewHolder>() {
    inner class LoaderViewHolder(val binding: ItemLoaderBinding):RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(ItemLoaderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}