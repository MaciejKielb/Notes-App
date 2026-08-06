package com.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    val title: String,
    val text: String,
    val createdOn: Long,
    val updatedOn: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
)
