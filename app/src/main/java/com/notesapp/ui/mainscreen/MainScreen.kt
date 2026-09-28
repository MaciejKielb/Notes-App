package com.notesapp.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import com.notesapp.ui.topbar.TopAppBar
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.notesapp.data.Notes
import com.notesapp.ui.theme.NotesAppTheme
import com.notesapp.ui.topbar.MainScreenDrawer

@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                drawerShape = RectangleShape,
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerTonalElevation = 0.dp,
            ) {
                MainScreenDrawer(
                    onCloseClick = {
                        scope.launch { drawerState.close()
                        }
                    },
                    onItemClick = {
                        scope.launch { drawerState.close()
                        }
                    },
                )
            }
        },
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = {
                TopAppBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    onAddNoteClick = {
                        // Handle add note click
                    }
                )
            },
        ) { innerPadding ->
                NotesPlaceholder(modifier = Modifier.padding(innerPadding))
            }
        }
    }

@Composable
fun NotesPlaceholder(
    notesList: List<Notes> = emptyList(),
    modifier: Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        if (notesList.isNotEmpty()) {
            LazyColumn() {
                items(notesList) { item ->
                    ListRow(note = item)
                }
            }
        } else {
            Text(
                text = "You don't have any notes yet.",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ListRow(note: Notes) {
    Card() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(name = "Main Screen", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewMainScreen() {
    NotesAppTheme {
        MainScreen()
    }
}
