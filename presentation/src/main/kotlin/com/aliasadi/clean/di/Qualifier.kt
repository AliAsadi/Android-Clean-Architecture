@file:Suppress("MatchingDeclarationName", "Filename")

package com.aliasadi.clean.di

import javax.inject.Qualifier

/**
 * @author by Ali Asadi on 28/01/2023
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppSettingsSharedPreference

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher