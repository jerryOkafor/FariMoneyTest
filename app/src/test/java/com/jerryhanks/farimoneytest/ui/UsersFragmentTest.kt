package com.jerryhanks.farimoneytest.ui

import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapp.data.respository.DummyApiDataSource
import com.google.common.truth.Truth.assertThat
import com.jerryhanks.fairmoneytest.TestUtils
import com.jerryhanks.fairmoneytest.provideFakeCoroutinesDispatcherProvider
import com.jerryhanks.fairmoneytest.viewAssertion.RecyclerViewItemCountAssertion
import com.jerryhanks.farimoneytest.R
import com.jerryhanks.farimoneytest.ui.users.UsersFragment
import com.jerryhanks.farimoneytest.ui.users.UsersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
class UsersFragmentTest {

    //mock the two dependencies of ViewModel : DataSource and DispatcherProvider
    @Mock
    lateinit var dataSource: DummyApiDataSource

    private lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UsersViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = UsersViewModel(dataSource, provideFakeCoroutinesDispatcherProvider())

        vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }


//        val app = getApplicationContext<App>()
//        app.setLocationProvider(mockLocationProvider)
    }


    @Test
    fun launchUsersFragmentAndVerifyUI() {
        val mockUsers = TestUtils.mockflow()
        val scenario = launchFragmentInContainer<UsersFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)

        scenario.onFragment { fragment ->
            fragment.viewModelProviderFactory = vmFactory
            assertThat(fragment.lifecycle.currentState).isEqualTo(Lifecycle.State.CREATED)
        }

        //drive scenario to resume and verify UI
        scenario.moveToState(Lifecycle.State.RESUMED)

        Espresso.onView(withId(R.id.swipeContainer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.recyclerView))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

        Mockito.`when`(dataSource.users(Mockito.anyBoolean())).thenReturn(mockUsers)

        //get the initial item count of recyclerView
        Espresso.onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(0))

        viewModel.getUsers(false)
        //drive to onViewCreated

        //Verify that dataSource is called
        Mockito.verify(dataSource, Mockito.atLeastOnce()).users(Mockito.anyBoolean())

    }
}