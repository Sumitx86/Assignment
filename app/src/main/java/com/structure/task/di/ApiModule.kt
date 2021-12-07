package com.structure.task.di

import android.net.sip.SipErrorCode.TIME_OUT
import com.structure.task.BuildConfig
import com.structure.task.model.NewsApi
import com.structure.task.model.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    private val BASE_URL = "https://newsapi.org"

    @Provides
    fun provideCountriesApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    fun provideCountriesService(): NewsService {
        return NewsService()
    }
}