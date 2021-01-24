package com.jerryhanks.farimoneytest.ui

import android.os.Build
import androidx.core.os.bundleOf
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
import com.google.common.truth.Truth
import com.jerryhanks.fairmoneytest.provideFakeCoroutinesDispatcherProvider
import com.jerryhanks.farimoneytest.R
import com.jerryhanks.farimoneytest.ui.userDetails.UserDetailsFragment
import com.jerryhanks.farimoneytest.ui.userDetails.UserDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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
class UserDetailsFragmentTest {

    //mock the two dependencies of ViewModel : DataSource and DispatcherProvider
    @Mock
    lateinit var dataSource: DummyApiDataSource

    private lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserDetailsViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = UserDetailsViewModel(dataSource, provideFakeCoroutinesDispatcherProvider())

        vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }
    }

    @Test
    fun launchFragmentAndVerifyUI() {
        val firstName = "FirstName"
        val lastName = "LastName"
        val title = "mr"
        val fullName = "$title $firstName $lastName"
        val email = "email@wxampledomain.com"

        val args = bundleOf(
            "userId" to "VqOy7pso6gmeEKnEEhob",
            "title" to "mr",
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "picture" to "https://randomuser.me/api/portraits/men/57.jpg"
        )

        val scenario = launchFragmentInContainer<UserDetailsFragment>(
            fragmentArgs = args
        )
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment ->
            fragment.viewModelProviderFactory = vmFactory
            Truth.assertThat(fragment.lifecycle.currentState).isEqualTo(Lifecycle.State.CREATED)

            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecyclerOwnwer ->
                if (viewLifecyclerOwnwer != null) {
                    // The fragment’s view has just been created
                    Truth.assertThat(viewLifecyclerOwnwer.lifecycle.currentState)
                        .isEqualTo(Lifecycle.State.CREATED)
                }

            }
        }

        //move to resumed state and verify UI
        scenario.moveToState(Lifecycle.State.RESUMED)

        //view assertions
        Espresso.onView(withId(R.id.tvDetailName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvDetailName))
            .check(ViewAssertions.matches(ViewMatchers.withText(fullName)))

        Espresso.onView(withId(R.id.tvDetailEmail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvDetailEmail))
            .check(ViewAssertions.matches(ViewMatchers.withText(email)))

    }

}