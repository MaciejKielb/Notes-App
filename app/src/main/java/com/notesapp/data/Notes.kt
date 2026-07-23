package com.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes (
    val title: String,
    val content: String,
    val createdAt: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)