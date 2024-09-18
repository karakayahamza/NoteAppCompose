package com.example.noteapp.data.model

// Note.kt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Note(
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
)