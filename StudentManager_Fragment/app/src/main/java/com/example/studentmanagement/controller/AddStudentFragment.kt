package com.example.studentmanagement.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.studentmanagement.R

class AddStudentFragment : Fragment(R.layout.layout_add_student_fragment) {

    interface OnStudentAddedListener {
        fun onStudentAdded(name: String, id: String)
    }

    private var listener: OnStudentAddedListener? = null

    private fun setOnStudentAddedListener(listener: OnStudentAddedListener?) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setOnStudentAddedListener(FragmentSingleton.getInstance().onStudentAddedListener)
        return inflater.inflate(R.layout.layout_add_student_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSave = view.findViewById<Button>(R.id.btn_add)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        btnSave.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.edit_name).text.toString()
            val id = view.findViewById<EditText>(R.id.edit_id).text.toString()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                listener?.onStudentAdded(name, id)
                parentFragmentManager.popBackStack()
            } else {
                // Xử lý lỗi nhập liệu
            }
        }

        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
