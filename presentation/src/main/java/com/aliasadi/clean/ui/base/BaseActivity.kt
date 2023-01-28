package com.aliasadi.clean.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.aliasadi.clean.R
import com.aliasadi.clean.di.core.AppSettingsSharedPreference
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    @Inject
    @AppSettingsSharedPreference
    lateinit var appSettings: SharedPreferences

    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    fun isDarkModeEnabled() = appSettings.getBoolean(DARK_MODE, false)

    fun enableDarkMode(enable: Boolean) = appSettings.edit().putBoolean(DARK_MODE, enable).commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (isDarkModeEnabled()) R.style.DarkTheme else R.style.LightTheme)
        setContentView(binding.root)
    }

    protected fun <T> LiveData<T>.observe(observer: Observer<in T>) = observe(this@BaseActivity, observer)

    companion object {
        const val DARK_MODE = "dark_mode"
    }
}