package com.example.tasking.repository

import android.content.Context
import com.example.tasking.data.task.TaskCompleted
import com.example.tasking.data.task.TaskCreate
import com.example.tasking.data.task.TaskEdit
import com.example.tasking.data.task.TaskGet
import com.example.tasking.data.task.TaskGetAll
import com.example.tasking.network.RetrofitClient
import retrofit2.Response

class HomeRepository (
    private val context: Context
){
    suspend fun getAll(search:String?, completed:Boolean?, ordering:String?, page:Int?): Response<TaskGetAll> {
        return RetrofitClient.getTaskService(context).getTasks(search, completed, ordering, page)
    }

    suspend fun createTask(name: String, description:String, priority:Int): Response<TaskGet> {
        return RetrofitClient.getTaskService(context).createTask(TaskCreate(name, description, priority))
    }

    suspend fun updateTask(id:Int, name: String, description:String, priority:Int, completed: Boolean): Response<TaskGet> {
        return RetrofitClient.getTaskService(context).updateTask(id,TaskEdit(name, description, priority, completed))
    }

    suspend fun deleteTask(id:Int):Response<Unit> {
        return RetrofitClient.getTaskService(context).deleteTask(id)
    }

    suspend fun completeTask(id:Int, completed: Boolean):Response<TaskGet>{
        return RetrofitClient.getTaskService(context).completeTask(id, TaskCompleted(completed))
    }
}
