package com.aliasadi.clean.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.aliasadi.clean.MovieDetailsGraphDirections
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.ActivityMovieDetailsBinding
import com.aliasadi.clean.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>() {

    private val args: MovieDetailsActivityArgs by navArgs()

    private val detailsNavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMovieDetailsBinding =
        ActivityMovieDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        detailsNavController.navigate(MovieDetailsGraphDirections.toMovieDetails(args.movieId))
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
        val Id = "movieId"

        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra(Id, movieId)
            context.startActivity(starter)
        }
    }
}