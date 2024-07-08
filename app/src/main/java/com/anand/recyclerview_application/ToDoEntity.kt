package com.anand.recyclerview_application

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = TaskDataClass::class,
        parentColumns = ["id"],
        childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE)
])

data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var taskId:Int?= 0,
    var todo:String?= null,
    var isCompeleted:Boolean? = false

)
