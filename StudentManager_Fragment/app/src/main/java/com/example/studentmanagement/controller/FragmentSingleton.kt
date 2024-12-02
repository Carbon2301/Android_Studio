package com.example.studentmanagement.controller

import com.example.studentmanagement.controller.AddStudentFragment.OnStudentAddedListener
import com.example.studentmanagement.model.Student

class FragmentSingleton private constructor() {
    var position: Int = 0
    var onStudentAddedListener: OnStudentAddedListener? = null
    var student: Student? = null
    var studentActionListener: StudentActionListener? = null

    companion object {
        // Lazy initialization for the singleton instance
        private var instance: FragmentSingleton? = null

        // Accessor to the singleton instance
        fun getInstance(): FragmentSingleton {
            if (instance == null) {
                instance = FragmentSingleton()
            }
            return instance!!
        }
    }


}
