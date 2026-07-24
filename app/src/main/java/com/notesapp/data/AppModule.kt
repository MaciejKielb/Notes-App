package com.notesapp.data

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule =
    module {
        single {
            Room
                .databaseBuilder(
                    androidContext(),
                    NotesDatabase::class.java,
                    "notes_database",
                ).build()
        }
        single { get<NotesDatabase>().dao }
    }
