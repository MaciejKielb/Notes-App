package com.notesapp.data

class NotesRepository (
    private val notesDao: NotesDao
): NotesRepositoryInterface {
    override suspend fun upsertNote(note: Notes) {
        notesDao.upsertNote(note)
    }

    override suspend fun deleteNote(note: Notes) {
        notesDao.deleteNote(note)
    }

    override fun getNoteOrderedByDate() = notesDao.getNoteOrderedByDate()

    override fun getNoteOrderedByUpdatedDate() = notesDao.getNoteOrderedByUpdatedDate()

    override fun getNoteOrderedByTitle() = notesDao.getNoteOrderedByTitle()
}