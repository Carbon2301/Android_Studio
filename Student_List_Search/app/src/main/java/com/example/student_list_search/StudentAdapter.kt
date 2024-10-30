package com.example.student_list_search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StudentAdapter(private val context: Context, private var studentList: List<Student>) : BaseAdapter() {

    fun updateList(newList: List<Student>) {
        studentList = newList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = studentList.size

    override fun getItem(position: Int): Student = studentList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val nameTextView = view.findViewById<TextView>(android.R.id.text1)
        val mssvTextView = view.findViewById<TextView>(android.R.id.text2)

        val student = getItem(position)
        nameTextView.text = student.name
        mssvTextView.text = student.mssv

        return view
    }
}
