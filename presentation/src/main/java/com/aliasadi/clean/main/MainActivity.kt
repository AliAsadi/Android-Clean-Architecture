package com.aliasadi.clean.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aliasadi.clean.R
import com.aliasadi.clean.base.BaseActivity
import com.aliasadi.clean.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var factory: MainViewModel.Factory

    private val navController by lazy { binding.container.getFragment<NavHostFragment>().navController }

    override fun createViewModel(): MainViewModel = ViewModelProvider(this, factory).get()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        supportActionBar?.setTitle(R.string.clean_architecture)

        when (binding.navigationView) {
            is NavigationBarView -> (binding.navigationView as NavigationBarView).setupWithNavController(navController)
        }
    }
}