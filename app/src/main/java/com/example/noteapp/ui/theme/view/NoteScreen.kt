package com.example.noteapp.ui.theme.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteSortOrder
import com.example.noteapp.ui.theme.composable.FilterSpinner
import com.example.noteapp.ui.theme.composable.NoteItem
import com.example.noteapp.ui.theme.state.NoteState
import com.example.noteapp.ui.theme.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(noteViewModel: NoteViewModel, navController: NavController) {
    val noteState by noteViewModel.noteState.collectAsState()
    val isGridLayout by noteViewModel.isGridLayout.collectAsState()
    val sortOptions = NoteSortOrder.entries.toTypedArray()

    LaunchedEffect(noteViewModel.currentSortOrder, noteViewModel.isAscending) {
        noteViewModel.fetchNotes(noteViewModel.currentSortOrder, noteViewModel.isAscending)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = if (isGridLayout) ImageVector.vectorResource(id = R.drawable.linear_layout) else ImageVector.vectorResource(
                                id = R.drawable.grid_layout
                            ),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Toggle Layout",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    noteViewModel.toggleLayout()
                                }
                        )


                        Spacer(modifier = Modifier.width(8.dp))

                        FilterSpinner(viewModel = noteViewModel, options = sortOptions)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("note_create") // noteId'yi sıfır olarak ele alacağız
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    when (noteState) {
                        is NoteState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is NoteState.Success -> {
                            val notes = (noteState as NoteState.Success).notes
                            if (isGridLayout) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    contentPadding = PaddingValues(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(notes) { note ->
                                        NoteItem(
                                            navController = navController,
                                            note = note,
                                            onDelete = { noteViewModel.delete(note) },
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(modifier = Modifier.padding(8.dp)) {
                                    items(notes) { note ->
                                        NoteItem(
                                            navController = navController,
                                            note = note,
                                            onDelete = { noteViewModel.delete(note) }
                                        )
                                    }
                                }
                            }
                        }

                        is NoteState.Error -> {
                            val exception = (noteState as NoteState.Error).exception
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Error: ${exception.localizedMessage}")
                            }
                        }
                    }
                }
            }
        }
    )
}