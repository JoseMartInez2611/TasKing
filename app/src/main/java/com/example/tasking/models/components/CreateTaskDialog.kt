package com.example.tasking.models.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.tasking.R

@Composable
fun CreateTaskDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSubmit: (String, String, Int) -> Unit
) {
    if(showDialog) {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var priority by remember { mutableStateOf(1) }
        val focusRequester = remember { FocusRequester() }
        val context = LocalContext.current
        val text = stringResource(R.string.Error_1)

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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequester.requestFocus()
                            }
                        )
                    )

                    SelectPriority(
                        onPriorityChange = { priority = it },
                        focusRequester = focusRequester
                    )

                    Description(
                        description = description,
                        onDescriptionChange = { description = it }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(title.isBlank() || description.isBlank()){
                            showToast(context, text)
                        }else {
                            onSubmit(title, description, priority)
                            onDismissRequest()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),

                ) {
                    Text(text = stringResource(R.string.CreateButton))
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(R.string.CancelButton))
                }
            }
        )
    }
}