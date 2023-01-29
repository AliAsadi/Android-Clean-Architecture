package com.aliasadi.clean.presentation.util

import org.mockito.Mockito

/**
 * @author by Ali Asadi on 29/01/2023
 */

/**
 * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 */
fun <T> any(): T = Mockito.any()

/**
 * Gives you the ability to mock observes programmatically
 * **/
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)