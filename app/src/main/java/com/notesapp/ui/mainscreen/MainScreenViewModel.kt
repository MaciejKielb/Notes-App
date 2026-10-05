package com.notesapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.Notes
import com.notesapp.data.NotesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    repository: NotesRepositoryInterface,
): ViewModel() {
    val getNotesOrderedByDateDesc: Flow<List<Notes>> = repository.getNotesOrderedByDateDesc().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val deleteNote: (Notes) -> Unit = { note ->
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

}