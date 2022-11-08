package com.aliasadi.clean.di.core.module

import android.content.Context
import android.content.SharedPreferences
import com.aliasadi.clean.util.ResourceProvider
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.data.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

/**
 * Created by Ali Asadi on 15/05/2020
 **/

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()
    }

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl
    }

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Named(PREFERENCE_APP_SETTINGS)
    fun provideAppSettingsSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_APP_SETTINGS, Context.MODE_PRIVATE)
    }

    companion object {
        const val PREFERENCE_APP_SETTINGS = "AppSettings"
    }
}