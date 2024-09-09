package com.example.noteapp

import com.example.noteapp.model.Note
import com.example.noteapp.model.Quote
import com.example.noteapp.roomdb.NoteDao
import com.example.noteapp.roomdb.QuoteService
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao,
    private val quoteService: QuoteService
) {

    // Veritabanı işlemleri
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    // API işlemleri
    suspend fun getQuoteByCity(category: String, appId: String): List<Quote> {
        return quoteService.getQuotes(category, appId)
    }
}
