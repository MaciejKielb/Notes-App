package com.notesapp.data

import kotlinx.coroutines.flow.Flow

interface NotesRepositoryInterface {
    suspend fun upsertNote(note: Notes)

    suspend fun deleteNote(note: Notes)

    fun getAllNotes(): Flow<List<Notes>>

    fun getNotesOrderedByDateDesc(): Flow<List<Notes>>

    fun getNotesOrderedByDateAsc(): Flow<List<Notes>>

    fun getNoteOrderedByTitleDesc(): Flow<List<Notes>>

    fun getNoteOrderedByTitleAsc(): Flow<List<Notes>>
}
