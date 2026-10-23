package com.notesapp.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.Notes
import com.notesapp.data.NotesRepositoryInterface
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteInputViewModel(private val notesRepository: NotesRepositoryInterface): ViewModel() {
    fun saveNote(
        noteId: Long,
        title: String,
        text: String,
        onSuccess: () -> Unit = {},
    ) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val createdOn = if (noteId != 0L) {
                notesRepository.getNoteById(noteId).first()?.createdOn ?: now
            } else {
                now
            }

            notesRepository.upsertNote(
                Notes(
                    id = noteId,
                    title = trimmedTitle,
                    text = text.trim(),
                    createdOn = createdOn,
                    updatedOn = now,
                )
            )
            onSuccess()
        }
    }

    fun getNoteById(noteId: Long, onResult: (Notes?) -> Unit) {
        viewModelScope.launch {
            val note = notesRepository.getNoteById(noteId).first()
            onResult(note)
        }
    }
}
