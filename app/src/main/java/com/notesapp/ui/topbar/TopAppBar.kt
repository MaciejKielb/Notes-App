package com.notesapp.ui.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar as M3TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notesapp.R
import com.notesapp.ui.theme.NotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onMenuClick: () -> Unit,
    onAddNoteClick: () -> Unit,
) {
    Column {
        M3TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = onMenuClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle drawer",
                    )
                }
            },
            actions = {
                IconButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = onAddNoteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add note",
                    )
                }
            },
        )
        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 2.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}

@Preview(name = "Top Bar", showBackground = true, widthDp = 390)
@Composable
fun TopAppBarPreview() {
    NotesAppTheme {
        TopAppBar(
            onMenuClick = {},
            onAddNoteClick = {},
        )
    }
}
