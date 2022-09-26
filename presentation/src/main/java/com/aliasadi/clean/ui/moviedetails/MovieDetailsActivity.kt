package com.aliasadi.clean.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.aliasadi.clean.MovieDetailsGraphDirections
import com.aliasadi.clean.R

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsActivity : AppCompatActivity() {

    private val args: MovieDetailsActivityArgs by navArgs()

    private val detailsNavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setContentView(R.layout.activity_movie_details)
        detailsNavController.navigate(MovieDetailsGraphDirections.toMovieDetails(args.movieId))
    }

    private fun setupViews() {
        setupActionBar()
    }

    private fun setupActionBar() = supportActionBar?.apply {
        setDisplayShowTitleEnabled(false)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra("movieId", movieId)
            context.startActivity(starter)
        }
    }
}