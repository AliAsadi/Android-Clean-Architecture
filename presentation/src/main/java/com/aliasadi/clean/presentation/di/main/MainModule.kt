package com.aliasadi.clean.presentation.di.main

import com.aliasadi.clean.presentation.main.MainViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import dagger.Module
import dagger.Provides

/**
 * @author by Ali Asadi on 07/08/2022
 **/
@Module
class MainModule {

    @Provides
    fun provideMainViewModelFactory(dispatchersProvider: DispatchersProvider): MainViewModel.Factory {
        return MainViewModel.Factory(dispatchersProvider)
    }

}