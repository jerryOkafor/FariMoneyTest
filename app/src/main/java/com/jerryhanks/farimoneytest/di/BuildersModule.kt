package com.jerryhanks.farimoneytest.di

import com.jerryhanks.farimoneytest.MainActivity
import com.jerryhanks.farimoneytest.ui.userDetails.UserDetailsFragment
import com.jerryhanks.farimoneytest.ui.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Author: Jerry Okafor
 * Project: TestApp
 * <p>
 * Date: 22/01/2021
 * <p>
 */

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    internal abstract fun contributeUsersDetailsFragment(): UserDetailsFragment
}