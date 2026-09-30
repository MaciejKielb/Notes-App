package com.notesapp.ui.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.notesapp.R
import com.notesapp.ui.topbar.BaseTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteInputScreen(
    onBackClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    var noteText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            NoteInputTopBar(
                onBackClick = {
                    onSaveClick(noteText)
                    onBackClick()
                },
                onSaveClick = { onSaveClick(noteText) }
            )
        },
    ) { innerPadding ->
        NoteInputContent(
            noteText = noteText,
            onNoteTextChange = { noteText = it },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun NoteInputTopBar(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    BaseTopBar(
        title = { Text(stringResource(R.string.note_input_screen_title)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onSaveClick) {
                Icon(Icons.Default.Done, contentDescription = "Save note")
            }
        },
    )
}

@Composable
fun NoteInputContent(
    noteText: String,
    onNoteTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = noteText,
            onValueChange = onNoteTextChange,
            label = { Text("Enter your note") },
            modifier = Modifier.fillMaxSize()
        )
    }
}