package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.aliasadi.clean.util.preview.PreviewContainer

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    onQueryChange: (query: String) -> Unit,
    onBackClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Search")
        },
        value = query,
        onValueChange = {
            query = it
            onQueryChange(it)
        }, leadingIcon = {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    query = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
        singleLine = true,
        maxLines = 1
    )
}

@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchViewPreview() {
    PreviewContainer {
        Row {
            Surface {
                SearchView({}, {})
            }
        }
    }
}