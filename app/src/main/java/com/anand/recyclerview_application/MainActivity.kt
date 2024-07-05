package com.anand.recyclerview_application

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anand.recyclerview_application.databinding.ActivityMainBinding
import com.anand.recyclerview_application.databinding.CustomDialogTaskBinding

class MainActivity : AppCompatActivity(), RecyclerInterface {
    var binding: ActivityMainBinding? = null
    lateinit var manager: LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter = TaskRecyclerAdapter(this, list, this)
    lateinit var taskDatabase: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        taskDatabase = TaskDatabase.getInstance(this)
        manager = LinearLayoutManager(this)
        binding?.recyclerView?.layoutManager = manager
        binding?.recyclerView?.adapter = adapter
        getData()
        binding?.rbAll?.setOnClickListener {
            list.clear()
           getData()
            adapter.notifyDataSetChanged()
        }
        binding?.rbLowP?.setOnClickListener {
            list.clear()
            list.addAll(taskDatabase.taskDao().taskAccPriority(3))
            adapter.notifyDataSetChanged()
        }
        binding?.rbMediumP?.setOnClickListener {
            list.clear()
            list.addAll(taskDatabase.taskDao().taskAccPriority(priority = 2))
            adapter.notifyDataSetChanged()
        }
        binding?.rbHighP?.setOnClickListener {
            list.clear()
           list.addAll(taskDatabase.taskDao().taskAccPriority(1))
            adapter.notifyDataSetChanged()
        }

        binding?.fab?.setOnClickListener {
            val dialog = Dialog(this)
            var dialogBinding = CustomDialogTaskBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text?.toString().isNullOrEmpty()) {
                    dialogBinding.etTitle.error = "enter Title"
                } else if (dialogBinding.etDes.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDes.error = "Enter Description"
                } else if (dialogBinding.rbGroup.checkedRadioButtonId == -1) {
                    Toast.makeText(this, "Set the Pirority", Toast.LENGTH_SHORT).show()
                } else {

                    var pirority = if (dialogBinding.rbHigh.isChecked) {
                        1
                    } else if (dialogBinding.rbMedium.isChecked) {
                        2
                    } else {
                        3
                    }
                    /*  list.add(
                          TaskDataClass(
                              title = dialogBinding.etTitle.text.toString(),
                              description = dialogBinding.etDes.text.toString(),
                              pirority
                          ))
                    adapter.notifyDataSetChanged()*/

                    taskDatabase.taskDao().insertTask(
                        TaskDataClass(
                            title = dialogBinding.etTitle.text.toString(),
                            description = dialogBinding.etDes.text.toString(),
                            priority = pirority
                        )
                    )
                    getData()
                    dialog.dismiss()
                }
            }
        }
    }

    fun getData(){
        list.clear()
        list.addAll(taskDatabase.taskDao().getTask())
        adapter.notifyDataSetChanged()
    }

    override fun update(position: Int) {
        Dialog(this).apply {
            var dialogBinding = CustomDialogTaskBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text.toString().isNullOrEmpty()) {
                    dialogBinding.etTitle.error = "enter Title"
                } else if (dialogBinding.etDes.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDes.error = "Enter Description"
                } else if (dialogBinding.rbGroup.checkedRadioButtonId == -1) {
                    Toast.makeText(this@MainActivity, "Set the pirority", Toast.LENGTH_SHORT).show()
                } else {

                    var pirority = if (dialogBinding.rbHigh.isChecked) {
                        1
                    } else if (dialogBinding.rbMedium.isChecked) {
                        2
                    } else {
                        3
                    }
                   /* list.set(
                        position,
                        TaskDataClass(
                            title = dialogBinding.etTitle.text.toString(),
                            description = dialogBinding.etDes.text.toString(),
                           priority = pirority
                        )
                    */
                    taskDatabase.taskDao().updateTask(
                        TaskDataClass(
                        id=0, title = dialogBinding?.etTitle?.text.toString()
                    )
                    )
                    adapter.notifyDataSetChanged()
                    dismiss()
                }

            }

        }
    }

    override fun delete(position: Int) {
        var deleteDialog = AlertDialog.Builder(this)
        deleteDialog.setTitle("Remove Task")
        deleteDialog.setMessage("Do you want to delete the task")
        deleteDialog.setPositiveButton("YES") { _, _ ->
            Toast.makeText(this@MainActivity, "Task Deleted", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }
        deleteDialog.setNegativeButton("NO") { _, _ ->
        }
        deleteDialog.show()
    }



}
