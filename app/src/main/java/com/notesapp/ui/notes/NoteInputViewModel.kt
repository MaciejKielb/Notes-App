package com.notesapp.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.Notes
import com.notesapp.data.NotesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteInputViewModel(
    private val notesRepository: NotesRepositoryInterface,
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private var noteId: Long = 0L
    private var createdOn: Long = 0L

    fun onTitleChange(value: String) {
        _title.value = value
    }

    fun onTextChange(value: String) {
        _text.value = value
    }

    fun load(noteId: Long) {
        this.noteId = noteId
        if (noteId == 0L) return

        viewModelScope.launch {
            val note = notesRepository.getNoteById(noteId).first() ?: return@launch
            createdOn = note.createdOn
            _title.value = note.title
            _text.value = note.text
        }
    }

    fun saveNote(onSuccess: () -> Unit = {}) {
        val trimmedTitle = _title.value.trim()
        if (trimmedTitle.isBlank()) return

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            notesRepository.upsertNote(
                Notes(
                    id = noteId,
                    title = trimmedTitle,
                    text = _text.value.trim(),
                    createdOn = createdOn.takeIf { noteId != 0L } ?: now,
                    updatedOn = now,
                )
            )
            onSuccess()
        }
    }
}