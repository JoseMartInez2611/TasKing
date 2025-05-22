package com.example.tasking.data.task

data class TaskGetAll (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<TaskGet>
)