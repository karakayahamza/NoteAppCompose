package com.example.noteapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.Converters
import com.example.noteapp.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        fun getDataBase(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context = context,
                NoteDatabase::class.java, "note_database"
            ).build()
        }
    }
}


