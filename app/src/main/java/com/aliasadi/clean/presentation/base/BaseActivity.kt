package com.aliasadi.clean.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.aliasadi.clean.presentation.di.DaggerInjector

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    private val daggerInjector: DaggerInjector by lazy { application as DaggerInjector }

    protected val viewModel: VM by lazy { createViewModel() }

    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }

    protected abstract fun createViewModel(): VM

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        daggerInjector.inject(this)
        setContentView(binding.root)
    }

    protected fun <T> LiveData<T>.observe(observer: Observer<in T>) = observe(this@BaseActivity, observer)
}