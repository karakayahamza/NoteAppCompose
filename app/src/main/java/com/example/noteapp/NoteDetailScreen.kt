package com.example.noteapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.ui.theme.viewmodel.NoteViewModel

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun NoteDetailScreen(viewmodel:NoteViewModel,modifier: Modifier = Modifier) {
//    var headline by remember { mutableStateOf("") }
//    var note by remember { mutableStateOf("") }
//
//    Scaffold(
//        content = { padding ->
//            Column(modifier = Modifier.padding(padding)) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    TextField(
//                        value = headline,
//                        onValueChange = { newValue ->
//                            headline = newValue
//                        },
//                        label = { Text(" ") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                )
//
//                Column(modifier = Modifier.fillMaxSize()) {
//                    OutlinedTextField(value = " ", onValueChange = { newValue ->
//                        note = newValue
//                    })
//
//                }
//
//            }
//        }
//    )
//}