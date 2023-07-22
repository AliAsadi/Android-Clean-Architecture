package com.aliasadi.clean.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.aliasadi.clean.di.core.AppSettingsSharedPreference
import com.aliasadi.clean.ui.navigation.AppGraph
import com.aliasadi.clean.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author by Ali Asadi on 07/08/2022
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val DARK_MODE = "dark_mode"
    }

    @Inject
    @AppSettingsSharedPreference
    lateinit var appSettings: SharedPreferences

    private fun isDarkModeEnabled() = appSettings.getBoolean(DARK_MODE, false)

    private fun enableDarkMode(enable: Boolean) = appSettings.edit().putBoolean(DARK_MODE, enable).commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            var darkMode by remember { mutableStateOf(isDarkModeEnabled()) }

            AppTheme(darkMode) {
                AppGraph(
                    navController = navController,
                    darkMode = darkMode,
                    onThemeUpdated = {
                        val updated = !darkMode
                        enableDarkMode(updated)
                        darkMode = updated
                    }
                )
            }
        }
    }

}