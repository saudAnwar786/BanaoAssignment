package com.example.banaoassignment.data.api

import com.example.banaoassignment.Constants
import com.example.banaoassignment.Constants.API_KEY
import com.example.banaoassignment.data.models.Images
import com.example.banaoassignment.data.models.Photo
import com.example.banaoassignment.data.models.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/services/rest")
     suspend fun getRecentPhotos(
        @Query("per_page") perPage:Int = 20,
        @Query("page") pageNo: Int = 1,
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("extras") extras: String = "url_s",
        @Query("text") query:String = "cat"
     ): Images

}