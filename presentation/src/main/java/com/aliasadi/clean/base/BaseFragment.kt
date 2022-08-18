package com.aliasadi.clean.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.aliasadi.clean.di.DaggerInjector

/**
 * @author by Ali Asadi on 07/08/2022
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    private val daggerInjector: DaggerInjector by lazy { requireActivity().application as DaggerInjector }

    protected val viewModel: VM by lazy { createViewModel() }

    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }

    protected abstract fun createViewModel(): VM

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        daggerInjector.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    protected fun <T> LiveData<T>.observe(observer: Observer<in T>) = observe(this@BaseFragment, observer)

}