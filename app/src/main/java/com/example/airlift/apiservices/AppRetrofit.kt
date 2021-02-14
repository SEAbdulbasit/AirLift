package com.example.airlift.apiservices

import com.example.airlift.datamodel.main.ImagesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//
// Created by Abdul Basit on 11/6/2020.
//

interface AppRetrofit {

    @GET("api/")
    fun getMovieImagesAsync(
        @Query(value = "page") page: Int = 0,
        @Query(value = "q") query: String? = null,
        @Query(value = "key") key: String = "11970216-69b90b3288af7654f2c0e1219"
    ): Deferred<Response<ImagesResponse>>

}