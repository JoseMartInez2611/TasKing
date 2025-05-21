package com.example.tasking.data.task

data class TaskCreate (
    val name: String,
    var description: String,
    var priority: Int
)