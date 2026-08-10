package com.notesapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {
    private lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    NotesDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testUpsertNote() =
        runTest {
            val firstNote = createTestNote(id = 1L, title = "first shopping list", text = "chicken, bread, ketchup")

            dao.upsertNote(firstNote)

            val allNotes = dao.getAllNotes().first()

            assertThat(allNotes).hasSize(1)

            val savedNote = allNotes[0]

            assertThat(savedNote.title).isEqualTo(firstNote.title)
            assertThat(savedNote.text).isEqualTo(firstNote.text)
            assertThat(savedNote.createdOn).isEqualTo(firstNote.createdOn)
        }

    @Test
    fun testDeleteNote() =
        runTest {
            val firstNote = createTestNote(id = 1L, title = "first shopping list")

            dao.upsertNote(firstNote)

            val allNotes = dao.getAllNotes().first().first()

            dao.deleteNote(allNotes)

            val allNotesAfterUpdate = dao.getAllNotes().first()

            assertThat(allNotesAfterUpdate).isEmpty()
        }

    @Test
    fun testGetAllNotes() =
        runTest {
            val firstNote = createTestNote(id = 1L, title = "first shopping list")
            val secondNote = createTestNote(id = 2L, title = "second shopping list", timeOffset = 3600000L)
            val thirdNote = createTestNote(id = 3L, title = "third shopping list", timeOffset = 7500000L)

            dao.upsertNote(firstNote)
            dao.upsertNote(secondNote)
            dao.upsertNote(thirdNote)

            val allNotes = dao.getAllNotes().first()

            assertThat(allNotes).hasSize(3)
        }

    @Test
    fun testGetNoteOrderedByDateDesc() =
        runTest {
            val firstNote = createTestNote(id = 1L, title = "first shopping list")
            val secondNote = createTestNote(id = 2L, title = "second shopping list", timeOffset = 3600000L)

            dao.upsertNote(firstNote)
            dao.upsertNote(secondNote)

            val allNotes = dao.getAllNotes().first()

            assertThat(allNotes).containsExactly(firstNote, secondNote).inOrder()
        }

    private fun createTestNote(
        id: Long,
        title: String = "Default Title",
        text: String = "Default Text",
        timeOffset: Long = 0L,
    ) = Notes(
        title = title,
        text = text,
        createdOn = System.currentTimeMillis() - timeOffset,
        updatedOn = System.currentTimeMillis() - timeOffset,
        id = id,
    )
}
