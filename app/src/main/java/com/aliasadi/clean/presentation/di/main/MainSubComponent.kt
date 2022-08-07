package com.aliasadi.clean.presentation.di.main

import com.aliasadi.clean.presentation.main.MainActivity
import dagger.Subcomponent

/**
 * @author by Ali Asadi on 07/08/2022
 **/
@Subcomponent(modules = [MainModule::class])
interface MainSubComponent {
    fun inject(mainActivity: MainActivity)
}