package com.jerryhanks.farimoneytest.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.testapp.di.Injectable
import com.jerryhanks.farimoneytest.App
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber

/**
 * Author: Jerry Okafor
 * Project: TestApp
 * <p>
 * Date: 22/01/2021
 * <p>
 */
open class ActivityLifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

}


object Injector {
    fun init(app: App) {
        DaggerAppComponent.builder()
            .application(app)
            .appModule(AppModule(app))
            .build()
            .inject(app)

        app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }


        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentAttached(
                    fm: FragmentManager,
                    f: Fragment,
                    context: Context
                ) {
                    if (f is Injectable) {
                        Timber.d("Injecting: $f")
                        AndroidSupportInjection.inject(f)
                    }
                }
            },
            true
        )
    }
}