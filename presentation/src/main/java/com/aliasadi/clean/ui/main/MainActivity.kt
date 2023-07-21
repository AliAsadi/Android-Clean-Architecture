package com.aliasadi.clean.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivityMainBinding
import com.aliasadi.clean.ui.base.BaseActivity
import com.aliasadi.clean.ui.base.BaseComposeActivity
import com.aliasadi.clean.ui.search.SearchActivity
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author by Ali Asadi on 07/08/2022
 */

@AndroidEntryPoint
class MainActivity : BaseComposeActivity() {

//    private val navController by lazy { binding.container.getFragment<NavHostFragment>().navController }

//    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(inflater)

    @Composable
    override fun ActivityContent() {
        MainScreen()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setupViews()
//    }
//
//    private fun setupViews() {
//        setupActionBar()
//        setupNavigationView()
//    }
//
//    private fun setupActionBar() = NavigationUI.setupActionBarWithNavController(
//        this,
//        navController,
//        AppBarConfiguration(setOf(R.id.feedFragment, R.id.favoritesFragment))
//    )
//
//    private fun setupNavigationView() = with(binding.navigationView) {
//        if (this is NavigationBarView) setupWithNavController(navController)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        @DrawableRes val darkModeIcon: Int = if (isDarkModeEnabled()) R.drawable.ic_dark_mode_fill else R.drawable.ic_dark_mode
//        menu?.findItem(R.id.action_dark_mode)?.icon = ContextCompat.getDrawable(this, darkModeIcon)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_search -> SearchActivity.start(this)
//            R.id.action_dark_mode -> {
//                enableDarkMode(!isDarkModeEnabled())
//                recreate()
//            }
//        }
//        return true
//    }
}