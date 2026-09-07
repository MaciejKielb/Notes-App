package com.notesapp.data

import androidx.room.Room
import com.notesapp.ui.gettingstarted.GettingStartedViewModel
import com.notesapp.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
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
        factory { get<NotesDatabase>().dao }
        single<NotesRepositoryInterface> { NotesRepository(get()) }
        viewModelOf(::SplashViewModel)
        viewModelOf(::GettingStartedViewModel)
        single { UserPreferences(androidContext()) }
    }
