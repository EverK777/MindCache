package com.example.mindvalleychallenge.data.external

import com.example.mindvalleychallenge.models.Publication
import com.example.mindvalleychallenge.utils.AppConstants
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private var apiService: Service = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(Service::class.java)



    suspend fun getPublications(): ApiResultHandle<List<Publication>>{
        return safeApiRequest(Dispatchers.IO) {apiService.getPublications()}
    }


    private suspend fun <T> safeApiRequest(dispatcher: CoroutineDispatcher, apiRequest: suspend () -> T): ApiResultHandle<T> {
        return withContext(dispatcher) {
            try {
                ApiResultHandle.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                ApiResultHandle.NetworkError
            }
        }
    }

}