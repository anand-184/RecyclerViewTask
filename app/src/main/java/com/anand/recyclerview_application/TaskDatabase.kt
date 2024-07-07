package com.anand.recyclerview_application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskDataClass::class,ToDoEntity::class], version = 1, exportSchema = true)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
    companion object{
        private var taskDatabase : TaskDatabase?= null
        fun getInstance(context: Context): TaskDatabase{
            if(taskDatabase == null){
                taskDatabase = Room.databaseBuilder(context,
                    TaskDatabase::class.java, "TaskDatabase")
                    .allowMainThreadQueries()
                    .build()
            }

            return taskDatabase!!
        }
    }
}