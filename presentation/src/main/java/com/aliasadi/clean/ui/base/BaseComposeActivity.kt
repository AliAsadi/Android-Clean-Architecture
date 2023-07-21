package com.aliasadi.clean.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.aliasadi.clean.di.core.AppSettingsSharedPreference
import com.aliasadi.clean.ui.theme.AppTheme
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseComposeActivity : AppCompatActivity() {

    @Inject
    @AppSettingsSharedPreference
    lateinit var appSettings: SharedPreferences
    fun isDarkModeEnabled() = appSettings.getBoolean(DARK_MODE, false)

    fun enableDarkMode(enable: Boolean) = appSettings.edit().putBoolean(DARK_MODE, enable).commit()

    @Composable
    protected abstract fun ActivityContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(isDarkModeEnabled()) {
                    ActivityContent()
            }
        }

    }

    companion object {
        const val DARK_MODE = "dark_mode"
    }
}