package com.example.tasking.models.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPriority(
    priority: Int = 1,
    onPriorityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(
        stringResource(R.string.ComboBoxLow),
        stringResource(R.string.ComboBoxMedium),
        stringResource(R.string.ComboBoxHigh)
    )
    var selectedOption by remember { mutableStateOf(options[priority-1]) }
    var focusManager = LocalFocusManager.current

    Text(
        text = stringResource(R.string.LabelPriority),
        modifier = Modifier.padding(vertical = 10.dp)
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        expanded = true
                    }
                }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                        onPriorityChange(index+1)
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            }
        }
    }
}