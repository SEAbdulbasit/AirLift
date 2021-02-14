package com.example.airlift.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageDetailsViewModel(imageUrl: String) : ViewModel() {
    val url = MutableLiveData<String>(imageUrl)

}