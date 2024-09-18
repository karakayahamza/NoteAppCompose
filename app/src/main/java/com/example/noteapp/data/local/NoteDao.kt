package com.example.noteapp.data.local

// NoteDao.kt
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY date ASC")
    fun getNotesSortedByDateAsc(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getNotesSortedByDateDesc(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY modificationDate ASC")
    fun getNotesSortedByModificationDateAsc(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY modificationDate DESC")
    fun getNotesSortedByModificationDateDesc(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY headline ASC")
    fun getNotesSortedByHeadlineAsc(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY headline DESC")
    fun getNotesSortedByHeadlineDesc(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
