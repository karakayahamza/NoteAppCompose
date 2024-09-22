package com.example.noteapp.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
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
    private val sharedPreferences =
        application.getSharedPreferences("note_preferences", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    private val _noteState = MutableStateFlow<NoteState>(NoteState.Loading)
    val noteState: StateFlow<NoteState> get() = _noteState

    private val _note = MutableLiveData<Note?>()
    val note: MutableLiveData<Note?> get() = _note

    private val _isGridLayout = MutableStateFlow(loadGridLayout())
    val isGridLayout: StateFlow<Boolean> get() = _isGridLayout

    var currentSortOrder: NoteSortOrder = loadSortOrder()
    var isAscending: Boolean = loadSortDirection()

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

    fun clearNote() {
        _note.value = null
    }

    private fun loadGridLayout(): Boolean {
        return sharedPreferences.getBoolean("isGridLayout", false)
    }

    private fun loadSortOrder(): NoteSortOrder {
        val ordinal = sharedPreferences.getInt("sortOrder", NoteSortOrder.DATE_DESC.ordinal)
        return NoteSortOrder.entries[ordinal]
    }

    private fun loadSortDirection(): Boolean {
        return sharedPreferences.getBoolean("isAscending", true)
    }


    fun toggleLayout() {
        _isGridLayout.value = !_isGridLayout.value
        editor.putBoolean("isGridLayout", _isGridLayout.value).apply()
        Log.d("NoteViewModel", "Grid Layout: ${_isGridLayout.value}")
    }


    fun setSortOrder(order: NoteSortOrder) {
        currentSortOrder = order
        editor.putInt("sortOrder", order.ordinal).apply()
    }

    fun setSortDirection(ascending: Boolean) {
        isAscending = ascending
        editor.putBoolean("isAscending", ascending).apply()
    }
}