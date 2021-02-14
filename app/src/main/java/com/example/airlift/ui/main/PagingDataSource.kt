package com.example.airlift.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.airlift.apiservices.repository.ImagesRepository
import com.example.airlift.datamodel.main.ImageDomainModel
import com.example.airlift.datamodel.main.asDomainModel
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

class PagingDataSource(
    private val imagesRepository: ImagesRepository,
    private val query: String?
) : PagingSource<Int, ImageDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDomainModel> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = imagesRepository.getImages(query = apiQuery, page = position)
            val nextKey =

                if ((response.body()?.hits == null) || (response.body()?.hits?.isEmpty() == true)) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + (params.loadSize / 40)
                }
            LoadResult.Page(
                data = response.body()?.hits?.asDomainModel() ?: mutableListOf(),
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageDomainModel>): Int? {
        return state.anchorPosition
    }
}