package com.anand.recyclerview_application

import android.text.format.Time
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class TaskDataClass(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title: String? = null,
    var description: String? = null,
    var priority: Int? = 0,
    var createdDate :  Date?= Calendar.getInstance().time,
    var createdTime : Date? = Calendar.getInstance().time
)

