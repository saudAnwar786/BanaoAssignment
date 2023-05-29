package com.example.banaoassignment.repository

import com.example.banaoassignment.Resource
import com.example.banaoassignment.api.ImageApi
import com.example.banaoassignment.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofit: ImageApi
){


    suspend fun getAllImages() : Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = retrofit.getRecentPhotos()
                emit(Resource.Loading())
                emit(Resource.Success(result.photos.photo))
                return@flow

            }catch (e: IOException){
                emit(Resource.Error("${e.printStackTrace()}"))

            }catch (e: HttpException){
                emit(Resource.Error("${e.printStackTrace()}"))

            }
        }
    }


}