package com.example.tasking.data.task

data class TaskUpdateAndGet (
    val id: Int,
    val name: String,
    var description: String,
    var priority: Int,
    var completed: Boolean
)