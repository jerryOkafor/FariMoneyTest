package com.jerryhanks.farimoneytest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jerryhanks.farimoneytest.ui.userDetails.UserDetailsViewModel
import com.jerryhanks.farimoneytest.ui.users.UsersViewModel
import com.jerryhanks.farimoneytest.utils.DummyViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindUsersFragmentViewModel(viewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    abstract fun bindUserDetailsFragmentViewModel(viewModel: UserDetailsViewModel): ViewModel


    @Binds
    abstract fun provideViewModelFactory(factory: DummyViewModelProviderFactory): ViewModelProvider.Factory
}