package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.NoteSortOrder
import com.example.noteapp.data.local.NoteDao
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.Quote
import com.example.noteapp.data.remote.QuoteService
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao,
    private val quoteService: QuoteService
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

    suspend fun getQuoteByCity(category: String, appId: String): List<Quote> {
        return quoteService.getQuotes(category, appId)
    }
}

