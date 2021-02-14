package com.example.airlift.di

import androidx.lifecycle.ViewModelProvider
import com.example.airlift.apiservices.createRetrofit
import com.example.airlift.apiservices.repository.ImagesRepository
import com.example.airlift.ui.details.DetailsViewModelFactory
import com.example.airlift.ui.main.ViewModelFactory

object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(): ImagesRepository {
        return ImagesRepository(createRetrofit())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }

    fun provideDetailsViewModelFactory(imageUrl: String): ViewModelProvider.Factory {
        return DetailsViewModelFactory(imageUrl)
    }
}