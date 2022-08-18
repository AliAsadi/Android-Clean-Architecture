package com.aliasadi.clean.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.R
import com.aliasadi.clean.base.BaseActivity
import com.aliasadi.clean.databinding.ActivityMainBinding
import com.aliasadi.clean.favorites.FavoritesFragment
import com.aliasadi.clean.feed.FeedFragment
import com.aliasadi.clean.main.MainViewModel.UiState.NavigateToFavoriteScreen
import com.aliasadi.clean.main.MainViewModel.UiState.NavigateToFeedScreen
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
        setupViews()
        setupObservers()
        setupListeners()
    }

    private fun setupViews() {
        supportActionBar?.setTitle(R.string.clean_architecture)
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

    private fun navigateToFavorite() = navigateToFragment(FavoritesFragment())

    private fun navigateToFeed() = navigateToFragment(FeedFragment())

    private fun navigateToFragment(fragment: Fragment) = supportFragmentManager.beginTransaction()
        .replace(binding.container.id, fragment)
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