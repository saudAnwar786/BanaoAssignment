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

        @Query("per_page") perPage:Int = 50,
        @Query("page") pageNo: Int = 1,
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("extras") extras: String = "url_s",
        @Query("text") text: String = "cat"
     ):Response<Photos>



}