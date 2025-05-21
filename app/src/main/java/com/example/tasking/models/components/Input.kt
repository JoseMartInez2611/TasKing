package com.example.tasking.models.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun Input(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    onNext: (() -> Unit)? = null,
    focusRequester: FocusRequester? = null,
    isPassword: Boolean = false
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = label,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 42.dp, bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNext?.invoke()
                }
            ),
            placeholder = {
                Text(
                    text = label,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                )
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None, // <--- NUEVO
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 25.dp)
                .padding(bottom = 25.dp)
                .then(
                    if (focusRequester != null) {
                        modifier.focusRequester(focusRequester)
                    } else {
                        modifier
                    }
                )
        )
    }
}
