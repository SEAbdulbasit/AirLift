package com.example.airlift.apiservices.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.airlift.apiservices.AppRetrofit
import com.example.airlift.datamodel.main.ImageDomainModel
import com.example.airlift.datamodel.main.ImagesResponse
import com.example.airlift.ui.main.PagingDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ImagesRepository(private val retrofit: AppRetrofit) : ImageServices {
    override suspend fun getImages(page: Int, query: String?): Response<ImagesResponse> {
        return retrofit.getMovieImagesAsync(
            query = query,
            page = page,
            key = "11970216-69b90b3288af7654f2c0e1219"
        )
            .await()
    }

    fun getSearchResultStream(query: String?): Flow<PagingData<ImageDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { PagingDataSource(this, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}