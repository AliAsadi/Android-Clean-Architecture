package com.aliasadi.clean.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivityMainBinding
import com.aliasadi.clean.ui.base.BaseActivity
import com.aliasadi.clean.ui.search.SearchActivity
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
        setupActionBar()
        setupNavigationView()
    }

    private fun setupActionBar() = NavigationUI.setupActionBarWithNavController(
        this,
        navController,
        AppBarConfiguration(setOf(R.id.feedFragment, R.id.favoritesFragment))
    )

    private fun setupNavigationView() = with(binding.navigationView) {
        if (this is NavigationBarView) setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> SearchActivity.start(this)
        }
        return true
    }
}