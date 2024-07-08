package com.anand.recyclerview_application

import androidx.room.Embedded
import androidx.room.Relation

 data class TaskShownList (
    @Embedded
    var taskDataClass:TaskDataClass,
            @Relation(entity= ToDoEntity::class,
                parentColumn = "id",
                entityColumn = "taskId")
            var todoList : List<ToDoEntity>

 )
