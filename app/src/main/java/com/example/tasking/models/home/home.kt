package com.example.tasking.models.home

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasking.R
import com.example.tasking.data.task.TaskGet
import com.example.tasking.ui.theme.TasKingTheme
import com.example.tasking.utils.ViewModelFactory


@Composable
fun HomeScreen(
    viewModel: HomeViewModel=viewModel(factory = ViewModelFactory(LocalContext.current)),
    navController: NavController,
) {

    val tasksState by viewModel.tasks.collectAsState()
    var currentPage by remember { mutableStateOf(1) }

    // Cada vez que cambie currentPage, consulta esa página
    LaunchedEffect(currentPage) {
        viewModel.getAll(page = currentPage)
    }

    val allTasks = tasksState?.results ?: emptyList()

    Scaffold(
        topBar = { topBar() },
        floatingActionButton={}

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PaginatedTaskList(tasks = allTasks, currentPage=currentPage, onPageChange = { newPage -> currentPage = newPage })
        }
    }
}

@Composable
fun taskItem(
    name: String,
    priority: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(16.dp),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            taskInformation(name, priority)
            Spacer(modifier = Modifier.weight(1f))
            taskAction()
        }
    }
}

@Composable
fun taskAction(modifier: Modifier = Modifier){
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.view),
            contentDescription = null,
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small)
                .clickable {  }
        )

        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )


    }
}


@Composable
fun taskInformation(
    name:String,
    priority:String,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier){
        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_small))

        )
        Text(
            text = priority,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp),
                    painter = painterResource(R.drawable.tasking_logo),

                    contentDescription = null
                )
            }
        },
        modifier = modifier.padding(
            bottom = 10.dp
        )
    )
}

@Composable
fun PaginatedTaskList(tasks: List<TaskGet>, currentPage: Int, onPageChange: (Int) -> Unit ) {

    val itemsPerPage = 5
    val totalPages = (tasks.size + itemsPerPage - 1) / itemsPerPage

    val currentItems = tasks
        .drop(currentPage * itemsPerPage)
        .take(itemsPerPage)

    Column {
        currentItems.forEach { task ->
            taskItem(name = task.name, priority = task.priority.toString())
        }
        Paginator(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageChange = onPageChange
        )
    }
}

@Composable
fun Paginator(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if (currentPage > 0) onPageChange(currentPage - 1)
            },
            enabled = currentPage > 0,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("<")
        }

        Text(
            text = "${currentPage + 1}",
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Button(
            onClick = {
                if (currentPage < totalPages - 1) onPageChange(currentPage + 1)
            },
            enabled = currentPage < totalPages - 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(">")
        }
    }
}