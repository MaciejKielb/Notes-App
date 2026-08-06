package com.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Upsert
    suspend fun upsertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY createdOn DESC")
    fun getNotesOrderedByDateDesc(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY updatedOn ASC")
    fun getNotesOrderedByDateAsc(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY title DESC")
    fun getNoteOrderedByTitleDesc(): Flow<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getNoteOrderedByTitleAsc(): Flow<List<Notes>>
}
