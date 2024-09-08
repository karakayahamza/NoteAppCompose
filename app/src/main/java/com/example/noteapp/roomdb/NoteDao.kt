package com.example.noteapp.roomdb

// NoteDao.kt
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>
        // LiveData or Flow

    //Insert it only add new item but Upsert if items already exist then update the item.
    @Upsert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}