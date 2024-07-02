package com.anand.recyclerview_application

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anand.recyclerview_application.databinding.ActivityMainBinding
import com.anand.recyclerview_application.databinding.CustomDialogTaskBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    lateinit var manager: LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter = TaskRecyclerAdapter(list)

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
        manager = LinearLayoutManager(this)
        binding?.recyclerView?.layoutManager = manager
        binding?.recyclerView?.adapter = adapter
        binding?.fab?.setOnClickListener {
            var dialog = Dialog(this)
            var dialogBinding = CustomDialogTaskBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text.toString().isNullOrEmpty()) {
                    dialogBinding.etTitle.error = "enter Title"
                } else if (dialogBinding.etDes.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDes.error = "Enter Description"
                }else if (dialogBinding.rbGroup.checkedRadioButtonId==-1) {
                    Toast.makeText(this, "Set the Pirority", Toast.LENGTH_SHORT).show()
                }else {
                    list.add(
                        TaskDataClass(
                            title = dialogBinding.etTitle.text.toString(),
                            description = dialogBinding.etDes.text.toString()
                        ))
                    if(dialogBinding.rbHigh.isChecked== true) {
                        binding?.recyclerView?.setBackgroundResource(R.color.Red)
                    }else if (dialogBinding.rbMedium.isChecked == true) {
                        binding?.recyclerView?.setBackgroundResource(R.color.blue)
                    }else{
                        binding?.recyclerView?.setBackgroundResource(R.color.blue)
                    }
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }


            }
        }




    }
