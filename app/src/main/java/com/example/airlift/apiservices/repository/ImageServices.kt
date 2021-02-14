package com.example.airlift.apiservices.repository

import com.example.airlift.datamodel.main.ImagesResponse
import retrofit2.Response

interface ImageServices {
    suspend fun getImages(page: Int = 0, query: String? = null): Response<ImagesResponse>
}