package com.aliasadi.clean.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aliasadi.clean.di.AppSettingsSharedPreference
import com.aliasadi.clean.ui.theme.AppTheme
import com.aliasadi.clean.ui.widget.NoInternetConnectionBanner
import com.aliasadi.domain.util.NetworkMonitor
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

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private fun isDarkModeEnabled() = appSettings.getBoolean(DARK_MODE, false)

    private fun enableDarkMode(enable: Boolean) = appSettings.edit().putBoolean(DARK_MODE, enable).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            var darkMode by remember { mutableStateOf(isDarkModeEnabled()) }

            LaunchedEffect(darkMode) {
                enableEdgeToEdge(
                    statusBarStyle = if (darkMode) {
                        SystemBarStyle.dark(android.graphics.Color.BLACK)
                    } else {
                        SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.BLACK)
                    }
                )
            }

            AppTheme(darkMode) {
                Surface {
                    Column(modifier = Modifier.statusBarsPadding()) {
                        val networkStatus by networkMonitor.networkState.collectAsState(null)

                        networkStatus?.let {
                            if (it.isOnline.not()) {
                                NoInternetConnectionBanner()
                            }
                        }

                        MainGraph(
                            mainNavController = navController,
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
    }
}