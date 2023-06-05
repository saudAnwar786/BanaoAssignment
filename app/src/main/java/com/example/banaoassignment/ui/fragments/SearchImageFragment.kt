package com.example.banaoassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.banaoassignment.Constants
import com.example.banaoassignment.R
import com.example.banaoassignment.databinding.FragmentSearchImageBinding
import com.example.banaoassignment.paging.ImagePagingAdapter
import com.example.banaoassignment.paging.LoaderPaginAdapter
import com.example.banaoassignment.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SearchImageFragment:Fragment(R.layout.fragment_search_image) {

    private val viewModel:MainViewModel by viewModels()
    private lateinit var imagePagingAdapter: ImagePagingAdapter
    private lateinit var binding: FragmentSearchImageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchImageBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        var job:Job? = null
        binding.etSearchImage.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.query.value = editable.toString()
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
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