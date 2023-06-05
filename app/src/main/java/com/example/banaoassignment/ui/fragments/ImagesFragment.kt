package com.example.banaoassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banaoassignment.R
import com.example.banaoassignment.databinding.FragmentImageBinding
import com.example.banaoassignment.paging.ImagePagingAdapter
import com.example.banaoassignment.paging.LoaderPaginAdapter
import com.example.banaoassignment.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImagesFragment :Fragment(R.layout.fragment_image) {


    private val viewModel:MainViewModel by viewModels()
    private lateinit var binding: FragmentImageBinding
    private lateinit var imagePagingAdapter: ImagePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentImageBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.list.observe(viewLifecycleOwner, Observer{
            imagePagingAdapter.submitData(lifecycle,it)
        })


    }


    private fun setUpRecyclerView(){
        imagePagingAdapter = ImagePagingAdapter()
        binding.rvImages.apply {
            adapter = imagePagingAdapter.withLoadStateHeaderAndFooter(
                footer = LoaderPaginAdapter(),
                header = LoaderPaginAdapter()
            )
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,2)
        }
    }
}