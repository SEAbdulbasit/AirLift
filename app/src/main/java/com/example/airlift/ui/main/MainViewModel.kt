package com.example.airlift.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.airlift.apiservices.Status
import com.example.airlift.apiservices.repository.ImagesRepository
import com.example.airlift.baseclasses.BaseViewModel
import com.example.airlift.datamodel.main.ImageDomainModel

class MainViewModel(private val repository: ImagesRepository) : BaseViewModel() {

    val initialDataLoading = MutableLiveData<Status>()
    val moreDataLoading = MutableLiveData(Status.SUCCESS)

    private var currentQueryValue: String? = null
    private var currentSearchResult: kotlinx.coroutines.flow.Flow<PagingData<ImageDomainModel>>? =
        null

    fun searchRepo(queryString: String?): kotlinx.coroutines.flow.Flow<PagingData<ImageDomainModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: kotlinx.coroutines.flow.Flow<PagingData<ImageDomainModel>> =
            repository.getSearchResultStream(queryString)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}