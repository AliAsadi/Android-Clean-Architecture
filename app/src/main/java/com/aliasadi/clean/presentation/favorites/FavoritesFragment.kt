package com.aliasadi.clean.presentation.favorites

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.FragmentFavoritesBinding
import com.aliasadi.clean.presentation.base.BaseFragment
import javax.inject.Inject

/**
 * @author by Ali Asadi on 03/08/2022
 */
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {

    @Inject
    lateinit var factory: FavoritesViewModel.Factory

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater)

    override fun createViewModel(): FavoritesViewModel = ViewModelProvider(this, factory).get()

}