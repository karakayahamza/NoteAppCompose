package com.example.noteapp.presentation.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.data.local.model.NoteSortOrder
import com.example.noteapp.domain.repository.NoteRepository
import com.example.noteapp.util.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    application: Application,
    private val customSharedPreferences: CustomSharedPreferences,
) : BaseViewModel(application) {


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
        try {
            noteRepository.insert(note)
            fetchNotes(currentSortOrder, isAscending)
        } catch (e: Exception) {
            Log.d("Exeption", e.message ?: "Unknown error")
        }
    }


    fun delete(note: Note) = viewModelScope.launch {
        try {
            noteRepository.delete(note)
        } catch (e: Exception) {
            Log.d("Exeption", e.message ?: "Unknown error")
        }
    }

    fun fetchNotes(sortOrder: NoteSortOrder, isAscending: Boolean) = viewModelScope.launch {
        setLoading()
        try {
            noteRepository.getNotes(sortOrder, isAscending).collect { notes ->
                setSuccess(notes)
            }
        } catch (e: Exception) {
            setError(e)
            Log.d("Exeption", e.message ?: "Unknown error")
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
        return customSharedPreferences.getLayoutType() ?: false
    }

    private fun loadSortOrder(): NoteSortOrder {
        val ordinal = customSharedPreferences.getOrderType() ?: 0
        return NoteSortOrder.entries[ordinal]
    }

    private fun loadSortDirection(): Boolean {
        return customSharedPreferences.getIsAscending() ?: false
    }

    fun toggleLayout() {
        _isGridLayout.value = !_isGridLayout.value
        customSharedPreferences.saveIsGrid(_isGridLayout.value)
    }

    fun setSortOrder(order: NoteSortOrder) {
        currentSortOrder = order
        customSharedPreferences.saveOrderType(order)
    }

    fun setSortDirection(ascending: Boolean) {
        isAscending = ascending
        customSharedPreferences.saveIsAscending(ascending)
    }
}