package com.example.noteapp.ui.states

import com.example.noteapp.data.local.model.Note

sealed class NoteState {
    data object Loading : NoteState()
    data class Success(val notes: List<Note>) : NoteState()
    data class Error(val exception: Throwable) : NoteState()
}
