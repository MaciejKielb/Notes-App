package com.notesapp.ui.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import com.notesapp.ui.topbar.TopAppBar
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.notesapp.data.Notes
import com.notesapp.ui.topbar.MainScreenDrawer

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val listOfNotes by viewModel.getNotesOrderedByDateDesc.collectAsStateWithLifecycle(initialValue = emptyList())

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
                        navController.navigate("note_input_screen")
                    }
                )
            },
        ) { innerPadding ->
                NotesPlaceholder(
                    notesList = listOfNotes,
                    modifier = Modifier.padding(innerPadding))
            }
        }
    }

@Composable
fun NotesPlaceholder(
    notesList: List<Notes> = emptyList(),
    modifier: Modifier
) {
    val listState = rememberLazyListState()
    LaunchedEffect(notesList.firstOrNull()?.id) {
        if (notesList.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        if (notesList.isNotEmpty()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = notesList,
                    key = { it.id }
                    ) { item ->
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            shape = RoundedCornerShape(16.dp),
            ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

//@Preview(name = "Main Screen", showBackground = true, widthDp = 390, heightDp = 844)
//@Composable
//fun PreviewMainScreen() {
//    NotesAppTheme {
//        MainScreen(navController = rememberNavController())
//    }
//}
