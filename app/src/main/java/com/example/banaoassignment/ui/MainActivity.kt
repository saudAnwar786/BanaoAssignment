package com.example.banaoassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banaoassignment.R
import com.example.banaoassignment.Resource
import com.example.banaoassignment.adapters.ImageAdapter
import com.example.banaoassignment.databinding.ActivityMainBinding
import com.example.banaoassignment.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var imageAdapter: ImageAdapter
    private val imageViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        imageViewModel.images.observe(this, Observer { result->
               when(result){
                   is Resource.Error ->
                   {
                       binding.progressBar.visibility = View.GONE
                       result.message?.let {
                           Snackbar.make(binding.root,"${result.message}",Snackbar.LENGTH_LONG).show()
                       }
                   }
                   is Resource.Loading ->
                   {
                       binding.progressBar.visibility = View.VISIBLE

                   }
                   is Resource.Success ->
                   {
                       binding.progressBar.visibility = View.GONE
                       result.data?.let { photos ->
                          imageAdapter.differ.submitList(photos.photo)
                       }
                   }
               }

        })
    }
    private fun setUpRecyclerView(){
        binding.rvImages.apply {
            imageAdapter = ImageAdapter()
            adapter = imageAdapter
            layoutManager = GridLayoutManager(this@MainActivity,2)

        }
    }
}