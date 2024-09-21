package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.local.database.NoteDao
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.data.local.model.NoteSortOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    fun getNotes(sortOrder: NoteSortOrder, isAscending: Boolean): Flow<List<Note>> {
        return when (sortOrder) {
            NoteSortOrder.DATE_DESC -> if (isAscending) noteDao.getNotesSortedByDateAsc() else noteDao.getNotesSortedByDateDesc()
            NoteSortOrder.MODIFICATION_DATE_DESC -> if (isAscending) noteDao.getNotesSortedByModificationDateAsc() else noteDao.getNotesSortedByModificationDateDesc()
            NoteSortOrder.HEADLINE_ASC -> if (isAscending) noteDao.getNotesSortedByHeadlineAsc() else noteDao.getNotesSortedByHeadlineDesc()
        }
    }

    fun getNoteById(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id)
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}