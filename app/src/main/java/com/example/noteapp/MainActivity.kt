package com.example.noteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.ui.screens.NoteDetailScreen
import com.example.noteapp.ui.screens.NoteScreen
import com.example.noteapp.ui.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                val noteViewModel: NoteViewModel = hiltViewModel()
                NavHost(navController = navController, startDestination = "note_screen") {
                    composable("note_screen") {
                        NoteScreen(noteViewModel, navController)
                    }
                    composable("detail_screen/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toInt() ?: 0
                        NoteDetailScreen(noteViewModel, noteId, navController)
                    }
                    composable("note_create") {
                        NoteDetailScreen(noteViewModel, noteId = 0, navController = navController)
                    }
                }
            }
        }
    }
}