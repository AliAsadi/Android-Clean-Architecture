package com.aliasadi.clean.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivitySearchBinding
import com.aliasadi.clean.ui.base.BaseActivity
import com.aliasadi.clean.ui.feed.MovieAdapter
import com.aliasadi.clean.ui.feed.MovieAdapterSpanSize
import com.aliasadi.clean.ui.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.ui.states.NavigationState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author by Ali Asadi on 25/09/2022
 */

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

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
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                getSearchUiState().collect {
                    binding.progressBar.isVisible = it.showLoading
                    binding.noMoviesFoundView.isVisible = it.showNoMoviesFound
                    movieAdapter.submitList(it.movies)
                    if (it.errorMessage != null) Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
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

    private fun setupRecyclerView(config: MovieAdapterSpanSize.Config = MovieAdapterSpanSize.Config(3)) = with(binding.recyclerView) {
        adapter = movieAdapter
        layoutManager = createMovieGridLayoutManager(config)
        setHasFixedSize(true)
        setItemViewCacheSize(0)
    }

    private fun createMovieGridLayoutManager(config: MovieAdapterSpanSize.Config): GridLayoutManager = GridLayoutManager(
        baseContext,
        config.gridSpanSize,
        RecyclerView.VERTICAL,
        false
    ).apply {
        spanSizeLookup = MovieAdapterSpanSize.Lookup(config, movieAdapter)
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