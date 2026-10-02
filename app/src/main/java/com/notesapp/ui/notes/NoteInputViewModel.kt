package com.notesapp.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.Notes
import com.notesapp.data.NotesRepositoryInterface
import kotlinx.coroutines.launch

class NoteInputViewModel(private val notesRepository: NotesRepositoryInterface): ViewModel() {
    fun saveNote(note: Notes) {
        viewModelScope.launch {
            notesRepository.upsertNote(note)
        }
    }
}