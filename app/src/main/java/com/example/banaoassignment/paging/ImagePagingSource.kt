package com.example.banaoassignment.paging

import android.media.Image
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banaoassignment.data.api.ImageApi
import com.example.banaoassignment.data.models.Images
import com.example.banaoassignment.data.models.Photo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ImagePagingSource @Inject constructor(
    private val api:ImageApi
) :PagingSource<Int,Photo>(){
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key?:1
        return try{
            val response = api.getRecentPhotos(pageNo = position)
            LoadResult.Page(data = response.photos.photo,prevKey = if(position == 1) null else position-1,
            nextKey = if(position == response.photos.pages) null else position+1)

        }catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }

    }
}