package com.example.airlift.baseclasses

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    private val viewModelJob = Job()
}
