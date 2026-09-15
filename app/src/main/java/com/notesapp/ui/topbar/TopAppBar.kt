package com.notesapp.ui.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar as M3TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notesapp.R
import com.notesapp.ui.theme.NotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onNavigationIconClick: () -> Unit,
) {
    Column(){
        M3TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onNavigationIconClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle drawer",
                    )
                }
            },
        )
        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 2.dp),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )
    }
}

@Preview(name = "Top Bar", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun TopAppBarPreview() {
    NotesAppTheme {
        TopAppBar(onNavigationIconClick = {})
    }
}
