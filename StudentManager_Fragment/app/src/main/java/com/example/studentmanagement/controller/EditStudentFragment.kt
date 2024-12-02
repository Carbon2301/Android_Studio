package com.example.studentmanagement.controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.studentmanagement.R
import com.example.studentmanagement.model.Student

class EditStudentFragment : Fragment() {

    private var listener: StudentActionListener? = null
    private var position: Int = 0
    private var student: Student? = null

    fun setup(listener: StudentActionListener?, position: Int, student: Student?) {
        this.listener = listener
        this.position = position
        this.student = student
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setup(FragmentSingleton.getInstance().studentActionListener, FragmentSingleton.getInstance().position, FragmentSingleton.getInstance().student)
        return inflater.inflate(R.layout.layout_fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val updateName = view.findViewById<EditText>(R.id.update_name)
        val updateID = view.findViewById<EditText>(R.id.update_id)
        val btnUpdate = view.findViewById<Button>(R.id.btn_update)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel_update)

        updateName.setText(student?.studentName)
        updateID.setText(student?.studentId)

        btnUpdate.setOnClickListener {
            val updatedName = updateName.text.toString()
            val updatedID = updateID.text.toString()

            if (updatedName.isNotEmpty() && updatedID.isNotEmpty()) {
                val updatedStudent = Student(updatedName, updatedID)
                listener?.onEditStudent(position, updatedStudent)
                Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
