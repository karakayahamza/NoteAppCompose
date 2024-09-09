package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.model.Note
import com.example.noteapp.roomdb.NoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository
    val allNotes: StateFlow<List<Note>>
    private val _quote = MutableStateFlow<String?>(null)
    val quote: StateFlow<String?> get() = _quote

    init {
        val noteDao = NoteDatabase.getDataBase(application).noteDao()
        val quoteService = RetrofitInstance.quoteService
        noteRepository = NoteRepository(noteDao, quoteService)
        allNotes = noteRepository.getAllNotes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

        val randomcat = CategoryManager.getRandomCategory()
        println("Category: ${randomcat}")
        fetchQuoteByCity("happiness")
        println("Quote: ${_quote.value}")
    }

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }

    private fun fetchQuoteByCity(category: String) = viewModelScope.launch {
        try {
            val fetchedQuote = noteRepository.getQuoteByCity(category, BuildConfig.API_KEY)
            _quote.value = fetchedQuote[0].quote
        } catch (e: Exception) {
            println("Exception: $e")
            _quote.value = null
        }
    }
}