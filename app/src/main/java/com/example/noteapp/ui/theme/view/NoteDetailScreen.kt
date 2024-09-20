package com.example.noteapp.ui.theme.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.theme.viewmodel.NoteViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    viewModel: NoteViewModel, noteId: Int, navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val note by viewModel.note.observeAsState()

    LaunchedEffect(noteId) {
        if (noteId != 0) {
            viewModel.getNoteById(noteId)
        } else {
            // Yeni bir not eklenirken eski verileri sıfırlayın
            viewModel.clearNote()  // viewModel'de notu sıfırlama fonksiyonu
            title = ""
            content = ""
        }
    }


    LaunchedEffect(note) {
        note?.let {
            title = it.headline
            content = it.text
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == 0) "Create Note" else "Edit Note") },
                actions = {
                    IconButton(onClick = {
                        val newNote = if (noteId == 0) {
                            Note(
                                text = content,
                                headline = title,
                                date = Calendar.getInstance().time,
                            )
                        } else {
                            note?.copy(headline = title, text = content, modificationDate = Date())
                        }

                        newNote?.let { viewModel.insert(it) }

                        navController.popBackStack()
                    }, content = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Save Button",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    })
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (noteId != 0 && note == null) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    maxLines = Int.MAX_VALUE,
                    singleLine = false
                )
            }
        }
    }
}