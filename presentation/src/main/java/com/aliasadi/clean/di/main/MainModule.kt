package com.aliasadi.clean.di.main

import com.aliasadi.clean.ui.main.MainViewModel
import com.aliasadi.data.util.DispatchersProvider
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