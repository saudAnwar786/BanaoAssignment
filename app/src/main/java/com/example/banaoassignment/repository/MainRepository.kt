package com.example.banaoassignment.repository

import com.example.banaoassignment.api.ImageApi
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofit: Retrofit
){


    suspend fun getRecentPhotos( pageNo:Int) = retrofit.create(ImageApi::class.java)
        .getRecentPhotos( pageNo = pageNo)


}