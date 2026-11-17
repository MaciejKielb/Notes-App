package com.notesapp.ui.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.notesapp.data.Notes
import com.notesapp.ui.topbar.MainScreenDrawer

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onAddNoteClick: () -> Unit,
    onEditNoteClick: (Long) -> Unit,
    openQrCodeScanner: () -> Unit,
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val listOfNotes by viewModel.getNotesOrderedByDateDesc.collectAsStateWithLifecycle(initialValue = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showNoteOptions by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Notes?>(null) }
    var selectedNote by remember { mutableStateOf<Notes?>(null) }

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
                        if (it.id == "QR Scanner") {
                            openQrCodeScanner()
                        }
                        scope.launch {
                            drawerState.close()
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
                        onAddNoteClick()
                    }
                )
            },
        ) { innerPadding ->
                NotesPlaceholder(
                    notesList = listOfNotes,
                    modifier = Modifier.padding(innerPadding),
                    onNoteClick = { note ->
                        onEditNoteClick(note.id)
                    },
                    onLongClick = { note ->
                        selectedNote = note
                        showNoteOptions = true
                    },
                )
        }
            if (showNoteOptions) {
                NoteOptionsBottomSheet(
                    onDismiss = { showNoteOptions = false },
                    onDeleteClick = {
                        noteToDelete = selectedNote
                        showNoteOptions = false
                        showDialog = true
                    },
                )
            }
            if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Are you sure you want to delete this note?") },
                text = { Text(text = "This cannot be undone.") },
                confirmButton = {
                    Text(
                        text = "OK",
                        modifier = Modifier
                            .clickable {
                                noteToDelete?.let { viewModel.deleteNote(it) }
                                showDialog = false
                            }
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                dismissButton = {
                    Text(
                        text = "Cancel",
                        modifier = Modifier
                            .clickable {
                                showDialog = false
                            }
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            ) }
        }
    }

@Composable
fun NotesPlaceholder(
    notesList: List<Notes> = emptyList(),
    modifier: Modifier,
    onNoteClick: (Notes) -> Unit = {},
    onLongClick: (Notes) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(notesList.firstOrNull()?.id) {
        if (notesList.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }
    Column (
        modifier = modifier.fillMaxSize(),
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
                            NoteCard(
                                note = item,
                                onNoteClick = {
                                    onNoteClick(item)
                                },
                                onLongClick = {
                                    onLongClick(item)
                                }
                            )
                    }
                }
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "You don't have any notes yet.",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun NoteCard(
    note: Notes,
    onNoteClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .combinedClickable(
                    onClick = onNoteClick,
                    onLongClick = onLongClick
                ),
            shape = RoundedCornerShape(16.dp),
            ) {
            Row {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )
            }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteOptionsBottomSheet(
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 72.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            NoteOption(
                icon = Icons.Default.Share,
                label = "Share Note",
            )
            NoteOption(
                icon = Icons.Outlined.Email,
                label = "Export Note",
            )
            NoteOption(
                icon = Icons.Default.Delete,
                label = "Delete Note",
                onClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun NoteOption(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Note Options", showBackground = true, widthDp = 390, heightDp = 390)
@Composable
fun PreviewNoteOptionsBottomSheet() {
    NoteOptionsBottomSheet(
        onDismiss = {},
        onDeleteClick = {},
    )
}

//@Preview(name = "Main Screen", showBackground = true, widthDp = 390, heightDp = 844)
//@Composable
//fun PreviewMainScreen() {
//    NotesAppTheme {
//        MainScreen(navController = rememberNavController())
//    }
//}
