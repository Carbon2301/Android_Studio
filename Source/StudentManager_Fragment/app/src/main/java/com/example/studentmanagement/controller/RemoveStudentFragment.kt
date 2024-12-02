package com.example.studentmanagement.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.studentmanagement.R
import com.example.studentmanagement.model.Student

class RemoveStudentFragment : Fragment() {

    private var listener: StudentActionListener? = null
    private var position: Int = -1
    private lateinit var student: Student

    fun setup(listener: StudentActionListener, position: Int, student: Student){
        this.listener = listener
        this.position = position
        this.student = student
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_remove, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnRemove = view.findViewById<Button>(R.id.btn_remove)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel_remove)

        btnRemove.setOnClickListener {
            val mainActivityContext = requireActivity()
            listener?.onRemoveStudent(position, student, mainActivityContext)
            parentFragmentManager.popBackStack()
        }

        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
