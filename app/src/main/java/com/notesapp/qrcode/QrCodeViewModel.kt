package com.notesapp.qrcode

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.data.NotesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QrCodeViewModel(
    private val notesRepository: NotesRepositoryInterface,
) : ViewModel() {

    private val _payload = MutableStateFlow<String?>(null)
    val payload: StateFlow<String?> = _payload.asStateFlow()

    private val _isTooLong = MutableStateFlow(false)
    val isTooLong: StateFlow<Boolean> = _isTooLong.asStateFlow()

    fun load(noteId: Long) {
        if (noteId == 0L) return

        viewModelScope.launch {
            val note = notesRepository.getNoteById(noteId).first() ?: return@launch
            val encodedTitle = Base64.encodeToString(note.title.toByteArray(), Base64.NO_WRAP)
            val encodedText = Base64.encodeToString(note.text.toByteArray(), Base64.NO_WRAP)
            val encoded = "$encodedTitle,$encodedText"

            if (encoded.length > 500) {
                _payload.value = null
                _isTooLong.value = true
            } else {
                _payload.value = encoded
                _isTooLong.value = false
            }
        }
    }
}
