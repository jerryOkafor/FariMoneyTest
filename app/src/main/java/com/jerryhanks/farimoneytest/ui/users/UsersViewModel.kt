package com.jerryhanks.farimoneytest.ui.users

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.respository.DummyApiDataSource
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import com.jerryhanks.farimoneytest.utils.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */


@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
class UsersViewModel @Inject
constructor(
    private val dummyDataSource: DummyApiDataSource,
    private val defaultDispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _result = MutableLiveData<Resource<List<UserMinimal>>>()
    val result: LiveData<Resource<List<UserMinimal>>>
        get() = _result


    fun getUsers(forceReload: Boolean = false) {
        viewModelScope.launch(defaultDispatcher.io) {
            dummyDataSource.users(forceReload = forceReload).collect { _result.postValue(it) }
        }
    }

}