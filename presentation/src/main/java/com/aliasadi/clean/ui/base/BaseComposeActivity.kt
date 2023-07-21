package com.aliasadi.clean.ui.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.aliasadi.clean.ui.theme.AppTheme

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseComposeActivity : AppCompatActivity() {

    @Composable
    protected abstract fun ActivityContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme(false) {
                ActivityContent()
            }
        }
    }
}