package com.example.tasking.network

import com.example.tasking.data.task.Task
import com.example.tasking.data.task.TaskCompleted
import com.example.tasking.data.task.TaskCreate
import com.example.tasking.data.task.TaskGet
import com.example.tasking.data.task.TaskGetAll
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @POST("tasks/")
    suspend fun createTask(@Body taskCreate: TaskCreate): Response<TaskGet>

    @GET("tasks/")
    suspend fun getTasks(
        @Query("search") search: String? = null,
        @Query("completed") completed: Boolean? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page:Int?=null
    ): Response<TaskGetAll>

    @GET("tasks/{id}/")
    suspend fun getTask(@Path("id") id: Int): Response<TaskGet>

    @PATCH("tasks/{id}/")
    suspend fun completeTask(@Path("id") id: Int, @Body taskCompleted: TaskCompleted): Response<TaskGet>

    @PATCH("tasks/{id}/")
    suspend fun updateTask(@Path("id") id: Int, @Body taskEdit: TaskCreate): Response<TaskGet>

    @DELETE("tasks/{id}/")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
