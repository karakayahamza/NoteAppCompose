package com.example.noteapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Note(
    //Colum name is 'Note'
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "headline")
    var headline: String,
    @ColumnInfo(name = "date")
    var date: Date,
    @ColumnInfo(name = "modificationDate")
    var modificationDate: Date = Date()
) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Int? = null,
// If you put it here. Upsert will not work. If you change anything in note it will effect to create a new note.
}