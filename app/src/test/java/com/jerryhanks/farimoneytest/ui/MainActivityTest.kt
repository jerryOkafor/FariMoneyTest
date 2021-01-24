package com.jerryhanks.farimoneytest.ui

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.jerryhanks.farimoneytest.MainActivity
import com.jerryhanks.farimoneytest.R
import com.jerryhanks.farimoneytest.ui.users.UsersFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()


    @Test
    fun `Launch MainActivity Verify UI is shown`() {
        val activityScenario = launchActivity<MainActivity>()

        activityScenario.onActivity { activity ->
            assertThat(activity.lifecycle.currentState).isEqualTo(Lifecycle.State.RESUMED)
        }
        activityScenario.moveToState(Lifecycle.State.STARTED)
        activityScenario.onActivity { activity ->
            assertThat(activity.lifecycle.currentState).isEqualTo(Lifecycle.State.STARTED)
        }
        activityScenario.moveToState(Lifecycle.State.CREATED)
        activityScenario.onActivity { activity ->
            assertThat(activity.lifecycle.currentState).isEqualTo(Lifecycle.State.CREATED)
        }

        activityScenario.onActivity { activity ->
            val navHost =
                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            assertThat(navHost).isInstanceOf(NavHostFragment::class.java)

            assertThat(navHost.childFragmentManager.fragments.first()).isInstanceOf(
                UsersFragment::class.java
            )
        }
    }
}