package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.model.Note
import com.example.noteapp.roomdb.NoteDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository
    val allNotes: StateFlow<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.getAllNotes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )
    }

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }
}