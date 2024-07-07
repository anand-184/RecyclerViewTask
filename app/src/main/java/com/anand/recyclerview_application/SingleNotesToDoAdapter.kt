package com.anand.recyclerview_application

import android.content.ContentValues.TAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SingleNotesToDoAdapter(var todoEntity :ArrayList<ToDoEntity>,var todoListInterface: TodoListInterface):RecyclerView.Adapter<SingleNotesToDoAdapter.ViewHolder> (){

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        var tvTodoItem = view.findViewById<TextView>(R.id.tvTodoItem)
        var cbTodo = view.findViewById<CheckBox>(R.id.cbTodo)
        var btnUpdateTodoItem = view.findViewById<Button>(R.id.btnUpdateTodoItem)
        var btnDeleteTodoItem = view.findViewById<Button>(R.id.btnDeleteTodoItem)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleNotesToDoAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_layout,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: SingleNotesToDoAdapter.ViewHolder, position: Int) {
        holder.tvTodoItem.setText(todoEntity[position].todo.toString())
        if(todoEntity[position].isCompeleted == true){
            holder.cbTodo.isChecked
        }
        holder.btnUpdateTodoItem.setOnClickListener {
            todoListInterface.updateTodoItem(position)
        }
        holder.btnDeleteTodoItem.setOnClickListener {
            todoListInterface.deleteTodoItem(position)
        }


    }

    override fun getItemCount(): Int {
        return todoEntity.size

    }
}