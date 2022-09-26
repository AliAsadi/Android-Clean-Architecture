package com.aliasadi.clean.di.search

import com.aliasadi.clean.ui.search.SearchActivity
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [SearchModule::class])
interface SearchSubComponent {
    fun inject(searchActivity: SearchActivity)
}