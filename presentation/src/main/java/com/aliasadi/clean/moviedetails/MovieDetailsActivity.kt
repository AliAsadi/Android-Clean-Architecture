package com.aliasadi.clean.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aliasadi.clean.R

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setContentView(R.layout.activity_movie_details)

        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            MovieDetailsFragment.createInstance(intent.getIntExtra(EXTRA_MOVIE_ID, 0))
        ).commitNow()
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
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(starter)
        }
    }
}