package com.anand.recyclerview_application

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskDataClass(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title: String? = null,
    var description: String? = null,
    var priority: Int? = 0
)

