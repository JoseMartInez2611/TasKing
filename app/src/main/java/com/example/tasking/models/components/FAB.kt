package com.example.tasking.models.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tasking.R

@Composable
fun FloatingButton(modifier: Modifier = Modifier, onClick: () -> Unit){

    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
        containerColor = MaterialTheme.colorScheme.inversePrimary,
        modifier=modifier
            .size(56.dp)
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(id= R.drawable.add),
            contentDescription = null,
            modifier = modifier
                .size(350.dp)
                .padding(20.dp)
                .clip(MaterialTheme.shapes.small)
        )

    }
}