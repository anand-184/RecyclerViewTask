package com.anand.recyclerview_application

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao

interface TaskDao {

    @Insert
    fun insertTask(taskDataClass: TaskDataClass)
    @Update
    fun updateTask(taskDataClass: TaskDataClass)
    @Delete
    fun deleteTask(taskDataClass: TaskDataClass)

    @Query("SELECT * FROM TaskDataClass")
    fun getList(): List<TaskDataClass>

    @Query("SELECT * FROM TaskDataClass WHERE priority=:priority")
    fun taskAccPriority(priority: Int) : List<TaskDataClass>

}