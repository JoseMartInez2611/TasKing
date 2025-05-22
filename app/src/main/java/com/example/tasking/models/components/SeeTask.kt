package com.example.tasking.models.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasking.R

@Composable
fun SeeTask(
    id:Int,
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    priority: Int,
    onPriorityChange: (Int) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSaveClick: (Int, String, Int, String, Boolean) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val verticalPadding = if (screenHeight < 600.dp) 5.dp else 10.dp

    Column (
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = verticalPadding)
            .fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.tasking_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = verticalPadding*3)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.LabelTitle),
                modifier = Modifier.padding(vertical = verticalPadding)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(R.string.LabelComplete))
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange
                )
            }
        }

        OutlinedTextField(
            value = taskName,
            onValueChange = onTaskNameChange,
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
            onPriorityChange = onPriorityChange
        )

        Description(
            description,
            onDescriptionChange = onDescriptionChange,
        )

        Row(
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                            onDeleteClick(id)
                            onBackClick()
                          },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(170.dp)
            ) {
                Text(
                    text = stringResource(R.string.DeleteButton),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = verticalPadding)
                )
            }

            Button(
                onClick ={
                            onSaveClick(id, taskName, priority, description, checked)
                            onBackClick()
                         },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(170.dp)
            ) {
                Text(
                    text = stringResource(R.string.SaveButton),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = verticalPadding)
                )
            }
        }

        Button(
            onClick = onBackClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.BackButton),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = verticalPadding)
            )
        }
    }
}
