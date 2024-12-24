package com.example.studentmanagementroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var mssvEditText: EditText
    private lateinit var hotenEditText: EditText
    private lateinit var ngaysinhEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var appDatabase: AppDatabase
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mssvEditText = findViewById(R.id.mssvEditText)
        hotenEditText = findViewById(R.id.hotenEditText)
        ngaysinhEditText = findViewById(R.id.ngaysinhEditText)
        emailEditText = findViewById(R.id.emailEditText)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)
        appDatabase = AppDatabase.getInstance(this)

        // Lấy `studentId` từ intent
        studentId = intent.getIntExtra("studentId", -1)

        // Load dữ liệu sinh viên từ cơ sở dữ liệu
        loadStudentDetails()

        // Cập nhật thông tin sinh viên
        updateButton.setOnClickListener {
            updateStudent()
        }

        // Xóa sinh viên
        deleteButton.setOnClickListener {
            deleteStudent()
        }
    }

    private fun loadStudentDetails() {
        CoroutineScope(Dispatchers.Main).launch {
            val student = withContext(Dispatchers.IO) {
                appDatabase.studentDao().getStudentById(studentId)
            }
            if (student != null) {
                mssvEditText.setText(student.mssv)
                hotenEditText.setText(student.hoten)
                ngaysinhEditText.setText(student.ngaysinh)
                emailEditText.setText(student.email)
            } else {
                Toast.makeText(this@DetailActivity, "Không tìm thấy sinh viên!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun updateStudent() {
        val mssv = mssvEditText.text.toString()
        val hoten = hotenEditText.text.toString()
        val ngaysinh = ngaysinhEditText.text.toString()
        val email = emailEditText.text.toString()

        if (mssv.isNotBlank() && hoten.isNotBlank() && ngaysinh.isNotBlank() && email.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                val student = Student(
                    id = studentId,
                    mssv = mssv,
                    hoten = hoten,
                    ngaysinh = ngaysinh,
                    email = email
                )
                appDatabase.studentDao().update(student)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetailActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent() {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.studentDao().deleteById(studentId)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailActivity, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
