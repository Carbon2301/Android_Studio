package com.example.studentmanagementroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementroom.databinding.ItemStudentBinding

class StudentAdapter(
    private val context: Context,
    private var studentList: List<Student>,
    private val onItemClickListener: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    // Lưu trạng thái checkbox
    private val selectedStudents = mutableSetOf<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentList[position]
        holder.mssvTextView.text = student.mssv
        holder.hotenTextView.text = student.hoten

        // Set trạng thái checkbox
        holder.checkbox.isChecked = selectedStudents.contains(student)

        // Lắng nghe sự kiện khi checkbox được nhấn
        holder.checkbox.setOnCheckedChangeListener(null) // Reset listener để tránh lỗi
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedStudents.add(student)
            } else {
                selectedStudents.remove(student)
            }
        }

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener { onItemClickListener(student) }
    }

    override fun getItemCount(): Int = studentList.size

    // Cập nhật danh sách sinh viên
    fun setStudentList(studentList: List<Student>) {
        this.studentList = studentList
        notifyDataSetChanged()
    }

    // Trả về danh sách sinh viên đã chọn
    fun getSelectedStudents(): List<Student> = selectedStudents.toList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mssvTextView: TextView = itemView.findViewById(R.id.mssvTextView)
        val hotenTextView: TextView = itemView.findViewById(R.id.hotenTextView)
        val checkbox: CheckBox = itemView.findViewById(R.id.studentCheckbox) // Thêm Checkbox
    }
}

