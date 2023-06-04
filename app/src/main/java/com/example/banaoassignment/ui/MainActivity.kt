package com.example.banaoassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banaoassignment.R
import com.example.banaoassignment.Resource
import com.example.banaoassignment.adapters.ImageAdapter
import com.example.banaoassignment.databinding.ActivityMainBinding
import com.example.banaoassignment.paging.ImagePagingAdapter
import com.example.banaoassignment.paging.LoaderPaginAdapter
import com.example.banaoassignment.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var imageAdapter: ImagePagingAdapter
    private val imageViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setUpRecyclerView()

        imageViewModel.list.observe(this,Observer{
            imageAdapter.submitData(lifecycle,it)
        })

    }

//    private fun subscribeToObservers(){
//        imageViewModel.images.observe(this, Observer { result->
//               when(result){
//                   is Resource.Error ->
//                   {
//                       binding.progressBar.visibility = View.GONE
//                       result.message?.let {
//                           Snackbar.make(binding.root,"${result.message}",Snackbar.LENGTH_LONG).show()
//                       }
//                   }
//                   is Resource.Loading ->
//                   {
//                       binding.progressBar.visibility = View.VISIBLE
//
//                   }
//                   is Resource.Success ->
//                   {
//                       binding.progressBar.visibility = View.GONE
//                       result.data?.let { photos->
//                          imageAdapter.differ.submitList(photos)
//                       }
//                   }
//               }
//
//        })
//    }
    private fun setUpRecyclerView(){
        imageAdapter = ImagePagingAdapter()
        binding.rvImages.apply {
            adapter = imageAdapter.withLoadStateHeaderAndFooter(
                footer = LoaderPaginAdapter(),
                header = LoaderPaginAdapter()
            )
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity,2)
        }
    }
}