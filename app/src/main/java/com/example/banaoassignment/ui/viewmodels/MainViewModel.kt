package com.example.banaoassignment.ui.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banaoassignment.Resource
import com.example.banaoassignment.models.Photos
import com.example.banaoassignment.repository.MainRepository
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val respository:MainRepository,
    @ApplicationContext private val context: Context
):ViewModel() {

   var images = MutableLiveData<Resource<Photos>>()
   init {
       getRecentImages(1)
   }

    fun getRecentImages(pageNo: Int) = viewModelScope.launch {
            safeGetImageCall(pageNo)
    }

    suspend fun safeGetImageCall(pageNo:Int){
        images.postValue(Resource.Loading())
         try{
             if(hasInternetConnection()){

                val response= respository.getRecentPhotos(pageNo)
                 Log.d("ViewModel","REspomse")
                images.postValue(handleImageResponse(response))
             }else{
                 images.postValue(Resource.Error("No Internet Connection"))
             }

         }catch (t: Throwable){
             when(t){
                 is IOException-> images.postValue(Resource.Error("Network Failure"))
                 else -> images.postValue(Resource.Error("Conversion Problem"))
             }
         }
    }
    private fun handleImageResponse(response: Response<Photos>) : Resource<Photos>{
        if(response.isSuccessful){
            response.body()?.let {
                 return Resource.Success(it)
            }

        }
            return Resource.Error(response.message())

    }
    private fun hasInternetConnection(): Boolean {

        val connectivityManager = Contexts.getApplication(context).getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}