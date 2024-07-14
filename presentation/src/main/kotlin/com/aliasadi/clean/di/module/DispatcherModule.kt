@file:Suppress("InjectDispatcher")

package com.aliasadi.clean.di.module

import com.aliasadi.clean.di.DefaultDispatcher
import com.aliasadi.clean.di.IoDispatcher
import com.aliasadi.clean.di.MainDispatcher
import com.aliasadi.domain.util.DispatchersProvider
import com.aliasadi.data.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): MainCoroutineDispatcher = Dispatchers.Main

    @Provides
    fun providesDispatcherProvider(
        @IoDispatcher io: CoroutineDispatcher,
        @MainDispatcher main: MainCoroutineDispatcher,
        @DefaultDispatcher default: CoroutineDispatcher
    ): DispatchersProvider {
        return DispatchersProviderImpl(
            io = io,
            main = main,
            default = default
        )
    }
}