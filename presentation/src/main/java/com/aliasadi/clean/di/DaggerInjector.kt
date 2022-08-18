package com.aliasadi.clean.di

/**
 * Created by Ali Asadi on 15/05/2020
 **/
interface DaggerInjector {
    fun <T> inject(view: T)
}