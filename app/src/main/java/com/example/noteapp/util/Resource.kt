package com.example.noteapp.util

import com.example.noteapp.data.local.model.Note

sealed class Resource {
    data object Loading : Resource()
    data class Success(val notes: List<Note>, val note: Note? = null) : Resource()
    data class Error(val exception: Throwable) : Resource()
}