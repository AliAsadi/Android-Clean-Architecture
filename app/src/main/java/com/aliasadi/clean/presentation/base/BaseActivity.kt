package com.aliasadi.clean.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.aliasadi.clean.presentation.di.DaggerInjector

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseActivity<VM : BaseViewModel>(
        @LayoutRes private val resId: Int
) : AppCompatActivity() {

    protected val daggerInjector: DaggerInjector by lazy { application as DaggerInjector }

    protected lateinit var viewModel: VM

    protected abstract fun createViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        setContentView(resId)
    }

    fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(this@BaseActivity, Observer {
            it?.let { observer(it) }
        })
    }
}