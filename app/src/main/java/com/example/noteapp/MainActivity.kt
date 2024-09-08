package com.example.noteapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.model.Note
import com.example.noteapp.ui.theme.NoteAppTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                NoteScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen() {
    val noteViewModel: NoteViewModel = viewModel()
    val notes by noteViewModel.allNotes.collectAsState()

    Scaffold(
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        LazyColumn {
                            items(notes) { note ->
                                NoteItem(note = note, onDelete = { noteViewModel.delete(note) })
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val newNote = Note(
                                text = "Sample Note",
                                headline = "Sample Headline",
                                date = Date()
                            )
                            noteViewModel.insert(newNote)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp) // Butonun etrafında boşluk bırakmak için
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Note"
                        )
                        Text("Yeni Note Ekle")
                    }
                }
            }
        }
    )
}


@Composable
fun NoteItem(note: Note, onDelete: () -> Unit) {
    Box(
        modifier = Modifier.border(
            BorderStroke(1.dp, color = MaterialTheme.colorScheme.onPrimary),
            shape = AbsoluteCutCornerShape(10)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = note.headline, style = MaterialTheme.typography.bodySmall)
                Text(text = note.text)
            }

//            IconButton(onClick = onDelete) {
//                Icon(Icons.Default.ArrowForward, contentDescription = "Go")
//            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteAppTheme {
    }
}