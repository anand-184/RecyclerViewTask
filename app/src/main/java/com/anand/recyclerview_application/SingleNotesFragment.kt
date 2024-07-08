package com.anand.recyclerview_application

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.anand.recyclerview_application.databinding.AddTodoDialogBinding
import com.anand.recyclerview_application.databinding.FragmentSingleNotesBinding
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleNotesFragment : Fragment(), TodoListInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentSingleNotesBinding? = null
    var taskDataClass = TaskDataClass()
    var toDoEntity = arrayListOf<ToDoEntity>()
    lateinit var toDoAdapter: SingleNotesToDoAdapter
    var taskDatabase: TaskDatabase? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var list = arrayListOf<TaskDataClass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleNotesBinding.inflate(layoutInflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskDatabase = TaskDatabase.getInstance(requireContext())
        toDoAdapter = SingleNotesToDoAdapter(toDoEntity, this)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.notesRecycler?.layoutManager = linearLayoutManager
        binding?.notesRecycler?.adapter = toDoAdapter

        arguments?.let {
            var notes = it.getString("notes")
            taskDataClass = Gson().fromJson(notes, TaskDataClass::class.java)
            binding?.etnotesTitle?.setText(taskDataClass?.title)
            binding?.etnotesDesc?.setText(taskDataClass?.description)
            getToDoList()
        }

        binding?.btnAddTodo?.setOnClickListener {
            var dialog = Dialog(requireContext())
            var dialogBinding = AddTodoDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.btnAddTolist.setOnClickListener {
                if (dialogBinding.etToDoitem.text.toString().isNullOrEmpty()) {
                    dialogBinding?.etToDoitem?.error = "Enter the item"
                } else {
                    taskDatabase?.taskDao()?.insertTodoItem(
                        ToDoEntity(
                            taskId = taskDataClass.id,
                            todo = dialogBinding.etToDoitem.text.toString(),
                            isCompeleted = true
                        )
                    )
                    toDoEntity.clear()
                    getToDoList()
                    toDoAdapter.notifyDataSetChanged()
                    dialog.dismiss()

                }
            }
        }
    }

    fun getToDoList() {
        toDoEntity.clear()
        toDoEntity.addAll(
            taskDatabase?.taskDao()?.getTodoList(taskId = taskDataClass.id) ?: arrayListOf()
        )
        toDoAdapter.notifyDataSetChanged()
    }

    override fun updateTodoItem(position: Int) {
        var dialogBinding: AddTodoDialogBinding? = null
        var dialog = Dialog(requireContext())
        dialogBinding = AddTodoDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.show()
        dialogBinding.etToDoitem.setText(toDoEntity[position].todo.toString())
        if (toDoEntity[position].isCompeleted == true) {
            dialogBinding.cbTodoitem.isChecked

        }
        dialogBinding.btnAddTolist.setOnClickListener {
            if (dialogBinding.etToDoitem.text.toString().toString().isNullOrEmpty()) {
                dialogBinding.etToDoitem.error = "Enter the Todo Item"
            } else {
                var isCompeleted = if (dialogBinding.cbTodoitem.isChecked == true) {
                    true
                } else {
                    false
                }
                taskDatabase?.taskDao()?.updateToDoItem(
                    todoEntity = ToDoEntity(
                        id = taskDataClass.id,
                        taskId = toDoEntity[position].taskId,
                        todo = dialogBinding.etToDoitem.toString(),
                        isCompeleted = isCompeleted
                    )
                )
                getToDoList()
                dialog.dismiss()

            }
        }
    }

    override fun deleteTodoItem(position: Int) {
        var deleteDialog = AlertDialog.Builder(requireContext())
        deleteDialog.setTitle("Delete Todo Item")
        deleteDialog.setMessage("Do you want to delete Todo")
        deleteDialog.setNegativeButton("No") { _, _ ->

        }
        deleteDialog.setPositiveButton("Yes") { _, _ ->
            taskDatabase?.taskDao()?.deleteToDoItem(toDoEntity[position])
            getToDoList()
            toDoAdapter.notifyDataSetChanged()
        }
        deleteDialog.show()

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
