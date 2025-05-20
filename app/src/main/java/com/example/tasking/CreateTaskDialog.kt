package com.example.tasking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CreateTaskDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSubmit: (String, String, Int) -> Unit
) {
    if(showDialog) {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var priority by remember { mutableStateOf(0) }

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {},
            text = {
                Column {
                    Text(
                        text = stringResource(R.string.LabelTitle),
                        Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text(text = stringResource(R.string.LabelTitle)) },
                    )

                    SelectPriority(
                        onPriorityChange = { priority = it }
                    )

                    Description(
                        description = description,
                        onDescriptionChange = { description = it }
                    )
                }
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        onSubmit(title, description, priority)
                        onDismissRequest()
                    },
                    shape = RoundedCornerShape(8.dp),

                ) {
                    Text(text = stringResource(R.string.CreateButton))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(R.string.CancelButton))
                }
            }
        )
    }
}