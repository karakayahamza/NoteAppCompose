package com.example.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.local.database.NoteDao
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.data.local.model.NoteSortOrder
import com.example.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val api: NoteDao) : NoteRepository {
    override fun getNotes(sortOrder: NoteSortOrder, isAscending: Boolean): Flow<List<Note>> {
        return when (sortOrder) {
            NoteSortOrder.DATE_DESC -> if (isAscending) api.getNotesSortedByDateAsc() else api.getNotesSortedByDateDesc()
            NoteSortOrder.MODIFICATION_DATE_DESC -> if (isAscending) api.getNotesSortedByModificationDateAsc() else api.getNotesSortedByModificationDateDesc()
            NoteSortOrder.HEADLINE_ASC -> if (isAscending) api.getNotesSortedByHeadlineAsc() else api.getNotesSortedByHeadlineDesc()
        }
    }

    override fun getNoteById(id: Int): LiveData<Note> {
        return api.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        api.insert(note)
    }

    override suspend fun delete(note: Note) {
        api.delete(note)
    }
}