package com.jerryhanks.farimoneytest.di

import android.app.Application
import com.jerryhanks.farimoneytest.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Author: Jerry Okafor
 * Project: TestApp
 * <p>
 * Date: 22/01/2021
 * <p>
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class, ViewModelModule::class,
        BuildersModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}