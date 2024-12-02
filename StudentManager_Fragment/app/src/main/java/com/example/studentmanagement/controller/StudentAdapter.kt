package com.example.studentmanagement.controller

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.OnStudentFragment
import com.example.studentmanagement.R
import com.example.studentmanagement.model.Student
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(private var students: MutableList<Student>,private var listener: OnStudentFragment) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(), StudentActionListener
{

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.text_student_name)
        val studentID: TextView = itemView.findViewById(R.id.text_student_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.studentName.text = student.studentName
        holder.studentID.text = student.studentId

        holder.itemView.setOnLongClickListener { view ->
            view.showContextMenu()

        }

        holder.itemView.setOnCreateContextMenuListener { menu, view, menuInfo ->
            (view.context as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_student, menu)
            menu.findItem(R.id.menu_edit).setOnMenuItemClickListener {
                listener.showEditStudentFragment(position, students[position])
                //showUpdateDialog(view.context, position)
                true
            }
            menu.findItem(R.id.menu_remove).setOnMenuItemClickListener {
                //listener.showRemoveStudentFragment(position, students[position])
                showRemoveDialog(view.context, position)
                true
            }
        }
    }

    private fun showUpdateDialog(context: Context, position: Int) {
        /*val student = students[position]
        val dialog = createDialog(context, R.layout.layout_dialog_edit)
        dialog.show()

        val updateName = dialog.findViewById<EditText>(R.id.update_name)
        val updateID = dialog.findViewById<EditText>(R.id.update_id)
        updateName.setText(student.studentName)
        updateID.setText(student.studentId)

        dialog.findViewById<Button>(R.id.btn_update).setOnClickListener {
            val updatedName = updateName.text.toString()
            val updatedID = updateID.text.toString()

            if (updatedName.isNotEmpty() && updatedID.isNotEmpty()) {
                students[position] = Student(updatedName, updatedID)
                Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.btn_cancel_update).setOnClickListener { dialog.dismiss() }*/
    }

    private fun showRemoveDialog(context: Context, position: Int) {
        val student = students[position]
        val dialog = createDialog(context, R.layout.layout_fragment_remove)
        dialog.show()

        dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {
            students.removeAt(position)
            notifyItemRemoved(position)

            val snackbar = Snackbar.make(
                (context as AppCompatActivity).findViewById(R.id.recycler_view_students),
                "Delete student successfully",
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction("Hoàn tác") {
                students.add(position, student)
                notifyItemInserted(position)
            }
            snackbar.show()
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_cancel_remove).setOnClickListener { dialog.dismiss() }
    }

    private fun createDialog(context: Context, layoutResId: Int): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutResId)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onEditStudent(position: Int, editStudent: Student) {
        val student = students[position]
        students[position] = editStudent
        notifyItemChanged(position)
    }

    override fun onRemoveStudent(position: Int, student: Student, context: Context) {
        students.removeAt(position)
        notifyItemRemoved(position)

        val snackbar = Snackbar.make(
            (context as AppCompatActivity).findViewById(R.id.recycler_view_students),
            "Delete student successfully",
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction("Hoàn tác") {
            students.add(position, student)
            notifyItemInserted(position)
        }
        snackbar.show()
    }
}

public interface StudentActionListener{
    fun onEditStudent(position: Int, editStudent: Student)
    fun onRemoveStudent(position: Int, student: Student, context: Context)
   // fun onInsertStudent(position: Int, student: Student)
}