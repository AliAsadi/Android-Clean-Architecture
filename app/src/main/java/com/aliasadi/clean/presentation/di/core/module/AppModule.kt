package com.aliasadi.clean.presentation.di.core.module

import android.content.Context
import com.aliasadi.clean.data.util.DiskExecutor
import com.aliasadi.clean.presentation.util.DispatchersProvider
import com.aliasadi.clean.presentation.util.DispatchersProviderImpl
import com.aliasadi.clean.presentation.util.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/

@Module
class AppModule constructor(context: Context) {

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Provides
    fun provideDiskExecutor(): DiskExecutor {
        return DiskExecutor()
    }

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl
    }

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }
}