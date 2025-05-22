package com.example.tasking.models.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasking.R
import com.example.tasking.data.task.TaskGet
import com.example.tasking.models.components.CreateTaskDialog
import com.example.tasking.models.components.FloatingButton
import com.example.tasking.models.components.LoadingScreen
import com.example.tasking.models.components.SeeTask
import com.example.tasking.models.components.showToast
import com.example.tasking.ui.theme.SegoeUI
import com.example.tasking.utils.ViewModelFactory
import kotlin.math.ceil


@Composable
fun HomeScreen(
    viewModel: HomeViewModel=viewModel(factory = ViewModelFactory(LocalContext.current)),
) {

    var refreshTrigger by remember { mutableStateOf(0) }
    val tasksState by viewModel.tasks.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var currentPage by remember { mutableStateOf(1) }
    var showDialog by remember { mutableStateOf(false) }
    var seeTask by remember { mutableStateOf(false) }
    var actualTask by remember { mutableStateOf<TaskGet?>(null) }
    var complete = stringResource(R.string.MessageComplete)
    var update =stringResource(R.string.MessageUpdate)
    var create = stringResource(R.string.MessageCreate)
    var delete = stringResource(R.string.MessageDelete)

    LaunchedEffect(refreshTrigger) {
        viewModel.getAll(page = currentPage)
    }

    val allTasks = tasksState?.results ?: emptyList()
    val totalItems=tasksState?.count
    var totalPages=1
    var context = LocalContext.current


    if(totalItems!=null){
        totalPages= ceil(totalItems / 5.0).toInt()
    }

    fun refresh() {
        refreshTrigger++
        currentPage = 1
    }

    CreateTaskDialog(
        showDialog=showDialog,
        onDismissRequest = {showDialog=false},
        onSubmit = {
            name, description, priority ->
            viewModel.createTask(name, description, priority)
            refresh()
            showToast(context, create)
        }
    )

    if (seeTask && actualTask != null) {
        var taskName by remember { mutableStateOf(actualTask!!.name) }
        var description by remember { mutableStateOf(actualTask!!.description) }
        var checked by remember { mutableStateOf(actualTask!!.completed) }
        var priority by remember { mutableStateOf(actualTask!!.priority) }

        SeeTask(
            id=actualTask!!.id,
            taskName = taskName,
            onTaskNameChange = { taskName = it },
            checked = checked,
            onCheckedChange = { checked = it },
            priority = priority,
            onPriorityChange = { priority = it },
            description = description,
            onDescriptionChange = { description = it },
            onSaveClick = {
                id,name,priority,description,completed ->
                viewModel.updateTask(id,name,priority,description,completed)
                showToast(context, update)
            },
            onDeleteClick = {
                id->viewModel.delteTask(id)
                showToast(context, delete)
            },
            onBackClick = {
                refresh()
                seeTask = false
            }
        )
    }else {
        Scaffold(
            topBar = { TopBar() },
            floatingActionButton = { FloatingButton(onClick = { showDialog = true }) }

        ) { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {
                var searchQuery by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(stringResource(R.string.LabelSearch)) },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null,
                            colorFilter= ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.image_size))
                                .padding(dimensionResource(R.dimen.padding_small))
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            currentPage = 1
                            viewModel.getAll(search = searchQuery)
                        }
                    )
                )

                if (loading) {
                    LoadingScreen()
                } else {
                    PaginatedTaskList(
                        tasks = allTasks,
                        currentPage = currentPage,
                        onPageChange = { newPage -> currentPage = newPage },
                        totalPages= totalPages,
                        onSeeTask = { task ->
                            actualTask = task
                            seeTask = true
                        },
                        onComplete = { task ->
                            viewModel.completeTask(
                                id = task.id,
                                completed = !task.completed
                            )
                            refresh()
                            showToast(context, complete)
                        },
                        refresh = {refresh()}
                    )
                }

            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskGet,
    onComplete: (TaskGet) -> Unit,
    seeTask: ()->Unit,
    name: String,
    priority: String,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val verticalPadding = if (screenHeight < 600.dp) 8.dp else 16.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(verticalPadding)
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
            TaskInformation(name, priority)
            Spacer(modifier = Modifier.weight(1f))
            TaskAction(
                checked = task.completed,
                onCheckedChange = { onComplete(task) },
                seeTask = seeTask
            )
        }
    }
}

@Composable
fun TaskAction(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    seeTask: ()-> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.view),
            contentDescription = null,
            colorFilter= ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer),
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    seeTask()
                }
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {onCheckedChange()}
        )
    }
}


@Composable
fun TaskInformation(
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
            fontFamily = SegoeUI,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(220.dp),
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
fun PaginatedTaskList(
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    totalPages: Int,
    tasks: List<TaskGet>,
    onSeeTask:(TaskGet)->Unit,
    onComplete: (TaskGet) -> Unit,
    refresh: () -> Unit
) {
    val currentItems = tasks

    Column {
        currentItems.forEach { task ->
            TaskItem(
                name = stringsCut(task.name),
                priority = "${stringResource(R.string.LabelPriority)} ${getPriority(task.priority)}",
                seeTask = {onSeeTask(task)},
                onComplete =  onComplete,
                task = task
            )
        }
        Paginator(
            currentPage = currentPage,
            totalPages = totalPages,
            onPageChange = onPageChange,
            refresh = refresh
        )
    }
}

@Composable
fun Paginator(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    refresh:()->Unit
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
                if (currentPage > 1) {
                    refresh()
                    onPageChange(currentPage - 1)
                }
            },
            enabled = currentPage > 1,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = "<",
                fontSize = 16.sp,
                )
        }

        Text(
            text = "$currentPage",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Button(
            onClick = {
                if (currentPage < totalPages) {
                    refresh()
                    onPageChange(currentPage + 1)
                }
            },
            enabled = currentPage < totalPages,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = ">",
                fontSize = 16.sp,
            )
        }
    }
}

fun stringsCut(value:String):String{

    if(value.length>20){
        return value.substring(0, 18)+"..."
    }else{

        return value
    }

}

@Composable
fun getPriority(value: Int):String{
    when(value)
    {
        1 -> return  stringResource(R.string.ComboBoxLow)
        2 -> return stringResource(R.string.ComboBoxMedium)
        else -> return stringResource(R.string.ComboBoxHigh)
    }
}