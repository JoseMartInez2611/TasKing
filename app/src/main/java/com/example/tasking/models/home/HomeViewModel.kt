package com.example.tasking.models.home

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasking.data.task.Task
import com.example.tasking.data.task.TaskGetAll
import com.example.tasking.models.auth.LoginState
import com.example.tasking.repository.AuthRepository
import com.example.tasking.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

    private val repository = HomeRepository(context)
    private val _tasks = MutableStateFlow<TaskGetAll?>(null)
    val tasks = _tasks.asStateFlow()

    fun getAll(search: String? = null, completed: Boolean? = null, ordering: String? = null, page:Int?=null) {
        viewModelScope.launch {
            val response = repository.getAll(search, completed, ordering, page)
            if (response.isSuccessful) {
                _tasks.value = response.body()
            }
        }
    }

}