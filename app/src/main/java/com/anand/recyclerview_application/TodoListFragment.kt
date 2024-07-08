package com.anand.recyclerview_application

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anand.recyclerview_application.databinding.CustomDialogTaskBinding
import com.anand.recyclerview_application.databinding.FragmentTodoListBinding
import com.google.gson.Gson

class TodoListFragment : Fragment(), RecyclerInterface {
    var binding: FragmentTodoListBinding? = null
    var list = arrayListOf<TaskShownList>()
    lateinit var manager: LinearLayoutManager
    lateinit var adapter: TaskRecyclerAdapter
    lateinit var taskDatabase: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskDatabase = TaskDatabase.getInstance(requireContext())
        adapter = TaskRecyclerAdapter(requireContext(), list, this)
        manager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding?.recyclerView?.layoutManager = manager
        binding?.recyclerView?.adapter = adapter
        getList()
        binding?.rbAll?.setOnClickListener {
            list.clear()
            getList()
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
            val dialog = Dialog(requireContext())
            var dialogBinding = CustomDialogTaskBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text?.toString().isNullOrEmpty()) {
                    dialogBinding.etTitle.error = "enter Title"
                } else if (dialogBinding.etDes.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDes.error = "Enter Description"
                } else if (dialogBinding.rbGroup.checkedRadioButtonId == -1) {
                    Toast.makeText(requireContext(), "Set the Pirority", Toast.LENGTH_SHORT).show()
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
                    getList()
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
    }
    fun getList(){
        list.clear()
        list.addAll(taskDatabase.taskDao().getList())
        adapter.notifyDataSetChanged()
    }


    override fun update(position: Int) {
        Dialog(requireContext()).apply {
            var dialogBinding = CustomDialogTaskBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            show()
            dialogBinding.etTitle.setText(list[position].title)
            dialogBinding.etDes.setText(list[position].description)
            when(list[position].priority){
                0->dialogBinding.rbLow.isChecked = true
                2->dialogBinding.rbMedium.isChecked = true
                1->dialogBinding.rbHigh.isChecked = true
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text.toString().isNullOrEmpty()) {
                    dialogBinding.etTitle.error = "enter Title"
                } else if (dialogBinding.etDes.text.toString().isNullOrEmpty()) {
                    dialogBinding.etDes.error = "Enter Description"
                } else if (dialogBinding.rbGroup.checkedRadioButtonId == -1) {
                    Toast.makeText(requireContext(), "Set the pirority", Toast.LENGTH_SHORT).show()
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
                        id=list[position].id, title = dialogBinding?.etTitle?.text.toString(),
                            description = dialogBinding?.etDes?.text.toString(),
                            priority = pirority
                    )
                    )
                    adapter.notifyDataSetChanged()
                    getList()
                    dismiss()
                }

            }

        }
    }

    override fun delete(position: Int) {
        var deleteDialog = AlertDialog.Builder(requireContext())
        deleteDialog.setTitle("Remove Task")
        deleteDialog.setMessage("Do you want to delete the task")
        deleteDialog.setPositiveButton("YES") { _, _ ->
            taskDatabase.taskDao().deleteTask(list[position])
            Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }
        deleteDialog.setNegativeButton("NO") { _, _ ->
        }
        deleteDialog.show()
        getList()
    }

    override fun itemClick(position: Int) {
        var convertToString = Gson().toJson(list[position])
        findNavController().navigate(R.id.singleNotesFragment, bundleOf("notes" to convertToString))
    }


}


