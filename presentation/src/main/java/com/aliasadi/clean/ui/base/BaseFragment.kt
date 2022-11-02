package com.aliasadi.clean.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

/**
 * @author by Ali Asadi on 07/08/2022
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    protected fun <T> LiveData<T>.observe(observer: Observer<in T>) = observe(this@BaseFragment, observer)

}