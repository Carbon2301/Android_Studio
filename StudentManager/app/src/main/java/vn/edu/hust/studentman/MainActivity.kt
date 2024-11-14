package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")

  )
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize the adapter
    studentAdapter = StudentAdapter(students, ::editStudent, ::deleteStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Handle "Add New" button click
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_text_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_text_id)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = editName.text.toString()
        val id = editId.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          val newStudent = StudentModel(name, id)
          students.add(newStudent)
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }


  private fun editStudent(position: Int) {
    val student = students[position]
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_text_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_text_id)


    // Pre-fill the existing student details
    editName.setText(student.studentName)
    editId.setText(student.studentId)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        val newName = editName.text.toString()
        val newId = editId.text.toString()
        if (newName.isNotEmpty() && newId.isNotEmpty()) {
          students[position] = StudentModel(newName, newId)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }


  private fun deleteStudent(position: Int) {
    val deletedStudent = students[position]
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    // Show Snackbar with Undo option
    Snackbar.make(findViewById(R.id.recycler_view_students), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, deletedStudent)
        studentAdapter.notifyItemInserted(position)
      }.show()
  }
}
