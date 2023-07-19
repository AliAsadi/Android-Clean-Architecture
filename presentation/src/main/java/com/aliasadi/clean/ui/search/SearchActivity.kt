package com.aliasadi.clean.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.Composable
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.base.BaseComposeActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author by Ali Asadi on 25/09/2022
 */

@AndroidEntryPoint
class SearchActivity : BaseComposeActivity() {

    private val viewModel: SearchViewModel by viewModels()

    @Composable
    override fun ActivityContent() {
        SearchPage(
            viewModel = viewModel,
            loadStateListener = { state, itemCount ->
                viewModel.onLoadStateUpdate(state, itemCount)
            },
            onMovieClick = viewModel::onMovieClicked
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        setupSearchView(menu?.findItem(R.id.action_search)?.actionView as SearchView)
        return true
    }

    private fun setupSearchView(searchView: SearchView) = with(searchView) {
        isIconified = false
        onActionViewExpanded()
        maxWidth = Integer.MAX_VALUE
        setQuery(viewModel.getSearchQuery(), false)

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onSearch(newText)
                return false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SearchActivity::class.java)
            context.startActivity(starter)
        }
    }

}