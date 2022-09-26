package com.aliasadi.clean.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.R
import com.aliasadi.clean.base.BaseActivity
import com.aliasadi.clean.databinding.ActivitySearchBinding
import com.aliasadi.clean.feed.MovieAdapter
import com.aliasadi.clean.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.search.SearchViewModel.*
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

    private fun setupObservers() = with(viewModel) {
        getUiState().observe {
            when (it) {
                is UiState.SearchUiState -> {
                    binding.progressBar.isVisible = it.showLoading
                    binding.noMoviesFoundView.isVisible = it.showNoMoviesFound
                    movieAdapter.submitList(it.movies)
                }

                is UiState.Error -> Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }

        getNavigationState().observe {
            when (it) {
                is NavigationState.MovieDetails -> MovieDetailsActivity.start(this@SearchActivity, it.movieId)
            }
        }
    }

    private fun setupViews() {
        setupActionBar()
        setupRecyclerView()
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