package com.jerryhanks.farimoneytest.ui.userDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.respository.DummyApiDataSource
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.User
import com.jerryhanks.farimoneytest.utils.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
class UserDetailsViewModel
@Inject constructor(
    private val dummyDataSource: DummyApiDataSource,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _resource = MutableLiveData<Resource<User>>()
    val resource: LiveData<Resource<User>>
        get() = _resource

    fun getUserDetails(userId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            dummyDataSource.userDetails(userId)
                .collect { _resource.postValue(it) }
        }
    }
}