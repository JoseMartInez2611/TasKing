package com.example.tasking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SeeTask(
    taskName: String,
    checked: Boolean,
    priority: Int,
    description: String,
    modifier: Modifier = Modifier
    ) {
    Column (
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.tasking_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Distribuye extremos
        ) {
            Text(text = stringResource(R.string.LabelTitle))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(R.string.LabelComplete))
                Switch(
                    checked = true,
                    onCheckedChange = { /*TODO*/ }
                )
            }
        }

        OutlinedTextField(
            value = taskName,
            onValueChange = { /*TODO*/ },
            placeholder = { Text(text = stringResource(R.string.LabelTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        SelectPriority(
            modifier = Modifier
                .fillMaxWidth()
        )

        Description(description)

        Row(
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Distribuye extremos
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(170.dp)
                    .height(50.dp)

            ) {
                Text(text = stringResource(R.string.SaveButton))
            }

            OutlinedButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(170.dp)
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.DeleteButton))
            }
        }

        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.BackButton))
        }
    }
}
