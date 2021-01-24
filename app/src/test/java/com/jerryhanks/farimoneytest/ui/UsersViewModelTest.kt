package com.jerryhanks.farimoneytest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.data.respository.DummyApiDataSource
import com.jerryhanks.fairmoneytest.MainCoroutineRule
import com.jerryhanks.fairmoneytest.TestUtils
import com.jerryhanks.fairmoneytest.provideFakeCoroutinesDispatcherProvider
import com.jerryhanks.fairmoneytest.runBlocking
import com.jerryhanks.farimoneytest.data.models.Resource
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import com.jerryhanks.farimoneytest.ui.users.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class UsersViewModelTest {
    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    var coroutinesRule = MainCoroutineRule()


    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var dummyDataSource: DummyApiDataSource

    @Mock
    lateinit var usersObserver: Observer<Resource<List<UserMinimal>>>

    lateinit var usersViewModel: UsersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        usersViewModel = UsersViewModel(dummyDataSource, provideFakeCoroutinesDispatcherProvider())
//
        usersViewModel.result.observeForever(usersObserver)
    }

    @Test
    fun `UsersViewModel should return list of users`() = coroutinesRule.runBlocking {
        val mockUserFlow = TestUtils.mockflow()

        Mockito.`when`(dummyDataSource.users(true)).thenReturn(mockUserFlow)

        usersViewModel.getUsers()

        Mockito.verify(dummyDataSource, Mockito.atLeast(1)).users(Mockito.anyBoolean())

    }


}