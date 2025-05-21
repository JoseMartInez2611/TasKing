package com.example.tasking.models.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.tasking.R

@Composable
fun SeeTask(
    taskName: String,
    checked: Boolean,
    priority: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column (
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.tasking_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.LabelTitle),
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(R.string.LabelComplete))
                Switch(
                    checked = checked,
                    onCheckedChange = { /*TODO*/ }
                )
            }
        }

        OutlinedTextField(
            value = taskName,
            onValueChange = { /*TODO*/ },
            placeholder = { Text(text = stringResource(R.string.LabelTitle)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        SelectPriority(
            priority = priority,
            onPriorityChange = { /*TODO*/ }
        )

        Description(
            description,
            onDescriptionChange = { /*TODO*/ },
        )

        Row(
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Distribuye extremos
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(170.dp)
                    .height(60.dp)

            ) {
                Text(text = stringResource(R.string.DeleteButton))
            }

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(170.dp)
                    .height(60.dp)
            ) {
                Text(text = stringResource(R.string.SaveButton))
            }
        }

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(60.dp)
        ) {
            Text(text = stringResource(R.string.BackButton))
        }
    }
}
