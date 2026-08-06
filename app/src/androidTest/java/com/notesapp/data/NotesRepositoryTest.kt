package com.notesapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat


@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {

    private lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testUpsertNote() = runTest {
        val note = Notes(
            "shopping list",
            "strawberries, potatoes, chicken",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
        )

        dao.upsertNote(note)

        val allNotes = dao.getNoteOrderedByDate().first()

        assertThat(allNotes).hasSize(1)

        val savedNote = allNotes[0]

        assertThat(savedNote.title).isEqualTo(note.title)
        assertThat(savedNote.text).isEqualTo(note.text)
        assertThat(savedNote.createdOn).isEqualTo(note.createdOn)
    }

    @Test
    fun testDeleteNote() = runTest {
        val note = Notes(
            "shopping list",
            "strawberries, potatoes, chicken",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
        )

        dao.upsertNote(note)

        val allNotes = dao.getNoteOrderedByDate().first().first()

        dao.deleteNote(allNotes)

        val allNotesAfterUpdate = dao.getNoteOrderedByDate().first()

        assertThat(allNotesAfterUpdate).isEmpty()
    }

    @Test
    fun testGetNoteOrderedByDate() = runTest {
        val firstNote = Notes(
            "first shopping list",
            "strawberries, potatoes, chicken",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            id = 1L
        )

        val secondNote = Notes(
            "second shopping list",
            "chips, beer, popcorn",
            System.currentTimeMillis()-3600000L,
            System.currentTimeMillis()-3600000L,
            id = 2L
        )

        dao.upsertNote(firstNote)
        dao.upsertNote(secondNote)

        val allNotes = dao.getNoteOrderedByDate().first()

        assertThat(allNotes).containsExactly( secondNote, firstNote).inOrder()
    }
}