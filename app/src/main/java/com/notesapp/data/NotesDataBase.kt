package com.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Notes::class],
    version = 1
)
abstract class NotesDataBe: RoomDatabase() {
    abstract val dao: NotesDao
}