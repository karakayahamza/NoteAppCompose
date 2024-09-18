package com.example.noteapp

import com.example.noteapp.data.model.Note

sealed class NoteState {
    data object Loading : NoteState()
    data class Success(val notes: List<Note>) : NoteState()
    data class Error(val exception: Throwable) : NoteState()
}
