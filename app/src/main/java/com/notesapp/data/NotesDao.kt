package com.notesapp.data

import androidx.room.Query
import androidx.room.Upsert
import androidx.room3.Delete
import kotlinx.coroutines.flow.Flow

interface NotesDao {

    @Upsert
    suspend fun upsertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getNoteOrderedByDate() : Flow<List<Notes>>
}