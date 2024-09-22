package com.example.noteapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.data.local.model.Note
import com.example.noteapp.ui.viewmodels.NoteViewModel
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
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(noteId) {
        if (noteId != 0) {
            viewModel.getNoteById(noteId)
        } else {
            viewModel.clearNote()
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
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Başlık") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.headlineSmall,

                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        val newNote = if (noteId == 0) {
                            Note(
                                text = content,
                                headline = title,
                                date = Calendar.getInstance().time,
                            )
                        } else {
                            note?.copy(
                                headline = title,
                                text = content,
                                modificationDate = Date()
                            )
                        }

                        newNote?.let { viewModel.insert(it) }

                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Save Button",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(1.dp)
        ) {
            Column {
                if (noteId != 0 && note == null) {
                    Text(
                        text = "Yükleniyor...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {

                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier
                            .fillMaxSize(),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        maxLines = Int.MAX_VALUE,
                        singleLine = false,
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )
                }
            }
        }
    }
}