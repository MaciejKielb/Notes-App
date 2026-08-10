package com.notesapp.data

class NotesRepository(
    private val notesDao: NotesDao,
) : NotesRepositoryInterface {
    override suspend fun upsertNote(note: Notes) {
        notesDao.upsertNote(note)
    }

    override suspend fun deleteNote(note: Notes) {
        notesDao.deleteNote(note)
    }

    override fun getAllNotes() = notesDao.getAllNotes()

    override fun getNotesOrderedByDateDesc() = notesDao.getNotesOrderedByDateDesc()

    override fun getNotesOrderedByDateAsc() = notesDao.getNotesOrderedByDateAsc()

    override fun getNoteOrderedByTitleDesc() = notesDao.getNoteOrderedByTitleDesc()

    override fun getNoteOrderedByTitleAsc() = notesDao.getNoteOrderedByTitleAsc()
}
