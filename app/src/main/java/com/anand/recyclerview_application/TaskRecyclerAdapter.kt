package com.anand.recyclerview_application

import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.SimpleTimeZone

class TaskRecyclerAdapter(var context: Context,var list: ArrayList<TaskShownList>,var taskRecyclerInterface: RecyclerInterface) :
    RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvtitle = view.findViewById<TextView>(R.id.tvTitle)
        var tvDesc = view.findViewById<TextView>(R.id.tvDescription)
        var tvDate = view.findViewById<TextView>(R.id.tvDate)
        var tvTime = view.findViewById<TextView>(R.id.tvTime)
        val itemLayout = view.findViewById<LinearLayout>(R.id.itemLayout)
        var todoList = view.findViewById<TextView>(R.id.tvTodolist)
        var btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtitle.setText(list[position].title)
        holder.tvDesc.setText(list[position].description)
        when (list[position].priority) {
            1 -> holder.itemLayout.setBackgroundResource(R.color.Red)
            2 -> holder.itemLayout.setBackgroundResource(R.color.green)
            3 -> holder.itemLayout.setBackgroundResource(R.color.blue)

        }
        holder.btnUpdate.setOnClickListener {
            taskRecyclerInterface.update(position)
        }
        holder.btnDelete.setOnClickListener {
            taskRecyclerInterface.delete(position)
        }
        holder.itemView.setOnClickListener {
            taskRecyclerInterface.itemClick(position)
        }
        holder.todoList.setText(list[position].todoList.toString())
        var createdDate = Calendar.getInstance()
        createdDate.time = list[position].taskDataClass.createdDate
        holder.tvDate.setText(SimpleDateFormat("dd,MMM,YYYY").format(createdDate.time))
        var createdTime = Calendar.getInstance()
        createdTime.time = list[position].taskDataClass.createdTime
        holder.tvTime.setText(SimpleDateFormat("hh:mm:ss").format(createdTime.time))

    }


}
