package com.aliasadi.clean.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.aliasadi.clean.di.core.AppSettingsSharedPreference
import com.aliasadi.clean.ui.theme.AppTheme
import javax.inject.Inject

/**
 * @author by Ali Asadi on 07/08/2022
 */
abstract class BaseComposeFragment : Fragment() {

    @Inject
    @AppSettingsSharedPreference
    lateinit var appSettings: SharedPreferences

    private fun isDarkModeEnabled() = appSettings.getBoolean(BaseActivity.DARK_MODE, false)

    @Composable
    protected abstract fun Content()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent {
                AppTheme(isDarkModeEnabled()) {
                    this@BaseComposeFragment.Content()
                }
            }
        }
}
