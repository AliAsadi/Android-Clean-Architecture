package com.aliasadi.clean.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivityMainBinding
import com.aliasadi.clean.presentation.base.BaseActivity
import com.aliasadi.clean.presentation.feed.FeedFragment
import com.aliasadi.clean.presentation.main.MainViewModel.UiState.NavigateToFavoriteScreen
import com.aliasadi.clean.presentation.main.MainViewModel.UiState.NavigateToFeedScreen
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var factory: MainViewModel.Factory

    private val bottomNavigationCallback by lazy { BottomNavigationCallback() }

    override fun createViewModel(): MainViewModel = ViewModelProvider(this, factory).get()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) navigateToFeed()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.bottomNavigation.setOnItemSelectedListener(bottomNavigationCallback)
    }

    private fun setupObservers() = with(viewModel) {
        getUiStateLiveData().observe {
            when (it) {
                is NavigateToFeedScreen -> navigateToFeed()
                is NavigateToFavoriteScreen -> navigateToFavorite()
            }
        }
    }

    private fun navigateToFavorite() {

    }

    private fun navigateToFeed() = supportFragmentManager.beginTransaction()
        .replace(binding.container.id, FeedFragment())
        .commitNow()

    override fun onDestroy() {
        binding.bottomNavigation.setOnItemSelectedListener(null)
        super.onDestroy()
    }

    inner class BottomNavigationCallback : NavigationBarView.OnItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return if (item.itemId == binding.bottomNavigation.selectedItemId) {
                false
            } else {
                when (item.itemId) {
                    R.id.feed -> viewModel.onFeedNavigationItemSelected()
                    R.id.favorites -> viewModel.onFavoriteNavigationItemSelected()
                }
                true
            }
        }
    }

}