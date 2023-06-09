package com.example.banaoassignment.ui.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.banaoassignment.Resource
import com.example.banaoassignment.data.models.Images
import com.example.banaoassignment.data.models.Photo
import com.example.banaoassignment.data.models.Photos
import com.example.banaoassignment.repository.MainRepository
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository:MainRepository,
    @ApplicationContext private val context: Context
):ViewModel() {

//    private val _response: MutableLiveData<List<Photo>> = MutableLiveData()
//    val response: LiveData<List<Photo>> = _response

   val list =  repository.getAllImages().cachedIn(viewModelScope)
    var query : MutableLiveData<String> = MutableLiveData("cat")
    val searchNews   = repository.getAllSearchedImages(query.value?:"cat")



//    fun getSearchImages(query:String) = viewModelScope.launch {
//            safeGetImageCall(query)
//    }
//    private fun getAllImages(){
//        viewModelScope.launch {
//            if(hasInternetConnection()) {
//                val result = repository.getAllImages()
//
//            }else
//            {
//                Log.d("MainViewModel","No internet connection")
//            }
//        }
//    }
//    suspend fun safeGetImageCall(pageNo:Int){
//        images.postValue(Resource.Loading())
//         try{
//             if(hasInternetConnection()){
//                val response = repository.getRecentPhotos(pageNo)
//                images.postValue(handleImageResponse(response))
//             }else{
//                 images.postValue(Resource.Error("No Internet Connection"))
//             }
//
//         }catch (t: Throwable){
//             when(t){
//                 is IOException-> images.postValue(Resource.Error("Network Failure"))
//                 else -> images.postValue(Resource.Error("Conversion Problem"))
//             }
//         }
//    }
//    private fun handleImageResponse(response: Response<Images>) : Resource<List<Photo>>{
//        if(response.isSuccessful){
//            response.body()?.let {
//                 return Resource.Success(it.photos.photo)
//            }
//        }
//            return Resource.Error(response.message())
//    }
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