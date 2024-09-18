package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.ui.theme.viewmodel.NoteViewModel
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                val viewModel = NoteViewModel(application)
                NavHost(navController = navController, startDestination = "note_screen") {
                    composable("note_screen") {
                        NoteScreen(navController)
                    }
                    composable("detail_screen/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0
                        NoteDetailScreen(viewModel, noteId, navController)
                    }
                    composable("note_create") {
                        NoteDetailScreen(viewModel, noteId = 0, navController = navController)
                    }
                }
            }
        }
    }
}


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
                    Button(onClick = {
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
                    }) {
                        Text("Save")
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavController) {
    val noteViewModel: NoteViewModel = viewModel()
    val notes by noteViewModel.allNotes.collectAsState()
    val listState = rememberLazyListState()
    val isGridLayout by noteViewModel.isGridLayout.collectAsState()

    LaunchedEffect(Unit) {
        noteViewModel.fetchNotes(noteViewModel.currentSortOrder, noteViewModel.isAscending)
    }

    LaunchedEffect(noteViewModel.allNotes) {
        noteViewModel.fetchNotes(noteViewModel.currentSortOrder, noteViewModel.isAscending)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween, // Space items evenly
                        verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
                    ) {
                        IconButton(onClick = {
                            noteViewModel.toggleLayout()
                        }) {
                            Icon(
                                imageVector = if (isGridLayout) Icons.Filled.List else Icons.Filled.Refresh,
                                contentDescription = "Toggle Layout"
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and filter spinner

                        FilterSpinner(viewModel = noteViewModel)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("note_create?noteId=null")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column(modifier = Modifier.fillMaxSize()) {
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
                        LazyColumn(state = listState) {
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
            }
        }
    )
}


@Composable
fun FilterSpinner(
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    val options = NoteSortOrder.entries.toTypedArray()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    var isAscending by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sort_icon_new),
                contentDescription = "Sort",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = if (isAscending) ImageVector.vectorResource(id = R.drawable.sort_up) else ImageVector.vectorResource(
                    id = R.drawable.sort_down
                ),
                contentDescription = "Sort Direction",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        isAscending = !isAscending
                        viewModel.fetchNotes(selectedOption, isAscending)
                    }
                    .size(20.dp)
            )
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .wrapContentSize()
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                        viewModel.fetchNotes(selectedOption, isAscending)
                    },
                    text = {
                        Text(
                            text = when (selectionOption) {
                                NoteSortOrder.DATE_DESC -> "Date"
                                NoteSortOrder.MODIFICATION_DATE_DESC -> "Modification Date"
                                NoteSortOrder.HEADLINE_ASC -> "Headline"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectionOption == selectedOption)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun NoteItem(
    navController: NavController,
    note: Note,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, color = MaterialTheme.colorScheme.onPrimary),
                shape = AbsoluteCutCornerShape(10)
            )
            .clickable {
                navController.navigate("detail_screen/${note.id}")
            }
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = note.headline,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
