package com.example.banaoassignment.api

import com.example.banaoassignment.Constants.API_KEY
import com.example.banaoassignment.models.Photo
import com.example.banaoassignment.models.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/services/rest/")
     suspend fun getRecentPhotos(
        @Query("method")
        method :String = "flickr.photos.getRecent",
        @Query("per_page")
         perPage: Int = 20,
        @Query("page")
        pageNo:Int = 1,
        @Query("api_key")
        apiKey:String = API_KEY
     ):Response<Photos>



}