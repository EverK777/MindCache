package com.example.mindvalleychallenge.data.external

import com.example.mindvalleychallenge.models.Publication
import retrofit2.http.GET

interface Service {

    @GET("wgkJgazE")
    suspend fun getPublications() : List<Publication>

}