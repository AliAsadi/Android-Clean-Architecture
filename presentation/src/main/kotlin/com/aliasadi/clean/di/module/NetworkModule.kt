package com.aliasadi.clean.di.module

import com.aliasadi.data.BuildConfig
import com.aliasadi.data.api.MovieApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}