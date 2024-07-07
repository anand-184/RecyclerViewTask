package com.anand.recyclerview_application

interface RecyclerInterface{
    fun update(position:Int)
    fun delete(position: Int)
    fun itemClick(position: Int)
}