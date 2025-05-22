package com.example.tasking.repository

import android.content.Context
import com.example.tasking.data.login.LoginRequest
import com.example.tasking.data.login.LoginResponse
import com.example.tasking.data.task.Task
import com.example.tasking.data.task.TaskCreate
import com.example.tasking.data.task.TaskGet
import com.example.tasking.data.task.TaskGetAll
import com.example.tasking.network.HomeService
import com.example.tasking.network.RetrofitClient
import com.example.tasking.utils.SessionManager
import retrofit2.Response

class HomeRepository (
    private val context: Context
){
    suspend fun getAll(search:String?, completed:Boolean?, ordering:String?, page:Int?): Response<TaskGetAll> {
        return RetrofitClient.getTaskService(context).getTasks(search, completed, ordering)
    }

}
