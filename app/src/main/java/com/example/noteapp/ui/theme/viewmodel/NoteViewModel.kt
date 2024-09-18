package com.example.noteapp.ui.theme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.BuildConfig
import com.example.noteapp.NoteSortOrder
import com.example.noteapp.utils.CategoryManager
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.local.NoteDatabase
import com.example.noteapp.data.remote.RetrofitInstance
import com.example.noteapp.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository
    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    val allNotes: StateFlow<List<Note>> get() = _allNotes
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note
    var currentSortOrder: NoteSortOrder = NoteSortOrder.DATE_DESC
    var isAscending: Boolean = true
    private val _isGridLayout = MutableStateFlow(false) // Track layout mode
    val isGridLayout: StateFlow<Boolean> get() = _isGridLayout

    init {
        val noteDao = NoteDatabase.getDataBase(application).noteDao()
        val quoteService = RetrofitInstance.quoteService
        noteRepository = NoteRepository(noteDao, quoteService)
        fetchNotes(currentSortOrder, isAscending)
    }

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
        fetchNotes(currentSortOrder, isAscending)
    }

    fun delete(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }

    fun fetchNotes(sortOrder: NoteSortOrder, isAscending: Boolean) = viewModelScope.launch {
        noteRepository.getNotes(sortOrder, isAscending).collect { notes ->
            _allNotes.value = notes
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            noteRepository.getNoteById(id).observeForever { note ->
                _note.value = note
            }
        }
    }

    fun toggleLayout() {
        _isGridLayout.value = !_isGridLayout.value
    }

    fun updateSortOrder(sortOrder: NoteSortOrder, ascending: Boolean) {
        currentSortOrder = sortOrder
        isAscending = ascending
        fetchNotes(currentSortOrder, isAscending)
    }
}

