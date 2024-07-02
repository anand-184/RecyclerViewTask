package com.anand.recyclerview_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskRecyclerAdapter(var list: ArrayList<TaskDataClass>):RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>(){
    class ViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        var tvtitle = view.findViewById<TextView>(R.id.tvTitle)
        var tvDesc = view.findViewById<TextView>(R.id.tvDescription)
        val itemLayout= view.findViewById<LinearLayout>(R.id.itemLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtitle.setText(list[position].title)
        holder.tvDesc.setText(list[position].description)
       // holder.itemView.id.

       // holder.itemLayout.
        //fun itemColor(number: Int) {
      //      when(number) {
       //         1->holder.itemLayout.setBackgroundResource(R.color.Red)
        //        2->holder.itemLayout.setBackgroundResource(R.color.green)
        //        3->holder.itemLayout.setBackgroundResource(R.color.blue)

         //   }

    }

    }
