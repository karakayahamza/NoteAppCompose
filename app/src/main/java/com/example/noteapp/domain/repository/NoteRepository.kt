package com.example.noteapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.data.local.model.NoteSortOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(sortOrder: NoteSortOrder, isAscending: Boolean): Flow<List<Note>>
    fun getNoteById(id: Int): LiveData<Note>
    suspend fun insert(note: Note)
    suspend fun delete(note: Note)
}