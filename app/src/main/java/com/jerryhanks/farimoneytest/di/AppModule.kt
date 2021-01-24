package com.jerryhanks.farimoneytest.di

import android.content.Context
import com.example.testapp.data.respository.DummyApiDataSource
import com.example.testapp.data.respository.DummyApiRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jerryhanks.farimoneytest.App
import com.jerryhanks.farimoneytest.BuildConfig
import com.jerryhanks.farimoneytest.data.api.DummyApiService
import com.jerryhanks.farimoneytest.data.db.AppDatabase
import com.jerryhanks.farimoneytest.utils.Constants
import com.jerryhanks.farimoneytest.utils.LocalDateTimeConverter
import com.jerryhanks.farimoneytest.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Singleton

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

@Module
class AppModule(val app: App) {

    @Singleton
    @Provides
    internal fun provideContext(): Context = app.applicationContext

    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    @Provides
    fun provideOkHttp(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
            .cache(cache)

        client.addInterceptor {
            val originalRequest = it.request()
            val newRequestBuilder = originalRequest.newBuilder()
                .header("app-id", BuildConfig.APP_ID)
                .url(originalRequest.url())

            if (NetworkMonitor.isNetworkConnected) {
                newRequestBuilder.addHeader("Cache-Control", "public, max-age=" + 5)
            } else {
                newRequestBuilder.addHeader(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                )
            }


            val request = newRequestBuilder.build()
            return@addInterceptor it.proceed(request)
        }

        return client.build()
    }

    @Provides
    fun provideDummyApiService(retrofit: Retrofit): DummyApiService =
        retrofit.create(DummyApiService::class.java)


    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
        .create()


    @Provides
    fun provideDummyDataSource(
        apiService: DummyApiService, appDatabase: AppDatabase,
    ): DummyApiDataSource = DummyApiRepository(apiService = apiService, appDatabase)


    @Provides
    fun provideDb(context: Context): AppDatabase = AppDatabase.getInstance(context = context, false)
}