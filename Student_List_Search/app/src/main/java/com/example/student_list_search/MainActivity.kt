package com.example.student_list_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchEditText: EditText
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchEditText = findViewById(R.id.searchEditText)

        // Tạo dữ liệu sinh viên mẫu
        generateStudents()

        // Khởi tạo adapter và gán cho ListView
        studentAdapter = StudentAdapter(this, studentList)
        listView.adapter = studentAdapter

        // Xử lý tìm kiếm
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(query: String) {
        val filteredList = if (query.length > 2) {
            studentList.filter {
                it.name.contains(query, ignoreCase = true) || it.mssv.contains(query, ignoreCase = true)
            }
        } else {
            studentList
        }
        studentAdapter.updateList(filteredList)
    }

    private fun generateStudents() {
        val lastNames = listOf("Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Phan", "Vũ", "Đặng", "Bùi", "Đỗ")
        val middleNames = listOf("Văn", "Thị", "Hữu", "Quốc", "Minh", "Ngọc", "Xuân", "Đức", "Huyền", "Tấn")
        val firstNames = listOf(
            "An", "Bình", "Cường", "Duy", "Dung", "Giang", "Hà", "Hải", "Khoa", "Lan",
            "Linh", "Loan", "Minh", "Nga", "Ngọc", "Phong", "Sơn", "Thảo", "Trung", "Yến"
        )

        for (i in 1..100) {
            val fullName = "${lastNames.random()} ${middleNames.random()} ${firstNames.random()}"
            studentList.add(Student(fullName, "MSSV${1000 + i}"))
        }
    }
}
