package com.example.noteapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.data.local.model.NoteSortOrder
import com.example.noteapp.repository.NoteRepository
import com.example.noteapp.ui.states.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository, application: Application
) : AndroidViewModel(application) {

    private val _noteState = MutableStateFlow<NoteState>(NoteState.Loading)
    val noteState: StateFlow<NoteState> get() = _noteState

    private val _note = MutableLiveData<Note?>()
    val note: MutableLiveData<Note?> get() = _note

    private val _isGridLayout = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> get() = _isGridLayout

    var currentSortOrder: NoteSortOrder = NoteSortOrder.DATE_DESC
    var isAscending: Boolean = true

    init {
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
        _noteState.value = NoteState.Loading
        try {
            noteRepository.getNotes(sortOrder, isAscending).collect { notes ->
                _noteState.value = NoteState.Success(notes)
            }
        } catch (e: Exception) {
            _noteState.value = NoteState.Error(e)
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

    fun clearNote() {
        _note.value = null
    }
}