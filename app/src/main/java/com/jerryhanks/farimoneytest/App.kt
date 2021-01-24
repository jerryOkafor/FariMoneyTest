package com.jerryhanks.farimoneytest

import android.app.Application
import com.jerryhanks.farimoneytest.di.Injector
import com.jerryhanks.farimoneytest.utils.NetworkMonitor
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Author: Jerry Okafor
 * Project: FariMoneyTest
 */

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Injector.init(this)

        NetworkMonitor.startNetworkCallback(this)

    }

    override fun androidInjector() = injector
}