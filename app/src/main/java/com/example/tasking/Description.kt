package com.example.tasking

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Description(
    description: String,
    modifier: Modifier = Modifier
) {
    var text  by remember { mutableStateOf(description) }

    Text(
        text = "Description:",
        modifier = modifier.padding(vertical = 10.dp)
    )

    OutlinedTextField(
        value = text,
        onValueChange = {text = it},
        placeholder = { Text(text="Enter here your description") },
        maxLines = 20,
        singleLine = false,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    )
}