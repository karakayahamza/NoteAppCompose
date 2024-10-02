package com.example.noteapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.presentation.ui.note_detail_screen.NoteDetailScreen
import com.example.noteapp.presentation.ui.note_screen.NoteScreen
import com.example.noteapp.presentation.ui.theme.NoteAppTheme
import com.example.noteapp.presentation.ui.viewmodels.NoteViewModel
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
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toInt()
                        NoteDetailScreen(noteViewModel, noteId, navController)
                        Log.d("Navigation", "$noteId")
                    }
                    composable("note_create") {
                        NoteDetailScreen(noteViewModel, noteId = 0, navController = navController)
                    }
                }
            }
        }
    }
}