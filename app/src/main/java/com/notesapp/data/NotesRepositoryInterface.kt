package com.notesapp.data

import kotlinx.coroutines.flow.Flow

interface NotesRepositoryInterface {
    suspend fun upsertNote(note: Notes)
    suspend fun deleteNote(note: Notes)
    fun getNoteOrderedByDate(): Flow<List<Notes>>
    fun getNoteOrderedByUpdatedDate(): Flow<List<Notes>>
    fun getNoteOrderedByTitle(): Flow<List<Notes>>
}