package com.example.tasking.data.task

data class TaskEdit (
    val name: String,
    var description: String,
    var priority: Int,
    var completed: Boolean
)