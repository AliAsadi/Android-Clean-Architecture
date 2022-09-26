package com.aliasadi.clean.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivitySearchBinding
import com.aliasadi.clean.ui.base.BaseActivity
import com.aliasadi.clean.ui.feed.MovieAdapter
import com.aliasadi.clean.ui.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.ui.search.SearchViewModel.Factory
import com.aliasadi.clean.ui.search.SearchViewModel.NavigationState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author by Ali Asadi on 25/09/2022
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    @Inject
    lateinit var factory: Factory

    private val movieAdapter by lazy { MovieAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

    override fun createViewModel(): SearchViewModel = ViewModelProvider(this, factory).get()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySearchBinding = ActivitySearchBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupActionBar()
        setupRecyclerView()
    }

    private fun setupObservers() = with(viewModel) {
        lifecycleScope.launch {
            getSearchUiState().collect {
                binding.progressBar.isVisible = it.showLoading
                binding.noMoviesFoundView.isVisible = it.showNoMoviesFound
                movieAdapter.submitList(it.movies)
                if (it.errorMessage != null) Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        }

        getNavigationState().observe {
            when (it) {
                is NavigationState.MovieDetails -> MovieDetailsActivity.start(this@SearchActivity, it.movieId)
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        adapter = movieAdapter
        setHasFixedSize(true)
        setItemViewCacheSize(0)
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

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onSearch(newText)
                return false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getImageFixedSize(): Int = applicationContext.resources.displayMetrics.widthPixels / 3

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SearchActivity::class.java)
            context.startActivity(starter)
        }
    }

}