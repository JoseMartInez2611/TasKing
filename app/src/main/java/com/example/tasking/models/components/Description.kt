package com.example.tasking.models.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.tasking.R

@Composable
fun Description(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = stringResource(R.string.LabelDescription),
        modifier = modifier.padding(vertical = 10.dp)
    )

    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        placeholder = { Text(text= stringResource(R.string.PlaceholderDescription)) },
        maxLines = 20,
        singleLine = false,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}