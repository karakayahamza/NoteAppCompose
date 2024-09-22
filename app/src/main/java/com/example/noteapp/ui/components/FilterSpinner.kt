package com.example.noteapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.data.local.model.NoteSortOrder
import com.example.noteapp.ui.viewmodels.NoteViewModel

@Composable
fun FilterSpinner(
    viewModel: NoteViewModel,
    options: Array<NoteSortOrder>,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(viewModel.currentSortOrder) }
    var isAscending by remember { mutableStateOf(viewModel.isAscending) }

    // Load the selected option and direction from the view model
    LaunchedEffect(viewModel.currentSortOrder, viewModel.isAscending) {
        selectedOption = viewModel.currentSortOrder
        isAscending = viewModel.isAscending
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sort_icon_new),
                contentDescription = "Sort",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expanded = !expanded }
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
                        viewModel.setSortDirection(isAscending)
                        viewModel.fetchNotes(selectedOption, isAscending)
                    }
                    .size(24.dp)
            )
        }

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
                        viewModel.setSortOrder(selectedOption)
                        viewModel.fetchNotes(selectedOption, isAscending)
                    },
                    text = {
                        Text(
                            text = selectionOption.description,
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
