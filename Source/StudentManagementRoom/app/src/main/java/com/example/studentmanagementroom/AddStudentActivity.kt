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

class AddStudentActivity : AppCompatActivity() {

    private lateinit var mssvEditText: EditText
    private lateinit var hotenEditText: EditText
    private lateinit var ngaysinhEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addButton: Button
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        mssvEditText = findViewById(R.id.mssvEditText)
        hotenEditText = findViewById(R.id.hotenEditText)
        ngaysinhEditText = findViewById(R.id.ngaysinhEditText)
        emailEditText = findViewById(R.id.emailEditText)
        addButton = findViewById(R.id.addButton)

        appDatabase = AppDatabase.getInstance(this)

        addButton.setOnClickListener {
            val mssv = mssvEditText.text.toString()
            val hoten = hotenEditText.text.toString()
            val ngaysinh = ngaysinhEditText.text.toString()
            val email = emailEditText.text.toString()

            // Kiểm tra xem các trường có trống không
            if (mssv.isNotBlank() && hoten.isNotBlank() && ngaysinh.isNotBlank() && email.isNotBlank()) {
                val student = Student(mssv = mssv, hoten = hoten, ngaysinh = ngaysinh, email = email)

                // Chạy trong Coroutine để tránh block UI thread
                CoroutineScope(Dispatchers.IO).launch {
                    // Thêm sinh viên vào cơ sở dữ liệu
                    appDatabase.studentDao().insert(student)

                    // Quay lại MainActivity sau khi thêm sinh viên
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddStudentActivity, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
                        finish() // Đóng AddStudentActivity và quay lại MainActivity
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
