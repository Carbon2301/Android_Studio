package com.example.studentmanagementroom

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private var studentList = mutableListOf<Student>()
    private lateinit var appDatabase: AppDatabase
    private lateinit var searchEditText: EditText

    private val activityScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        appDatabase = AppDatabase.getInstance(this)

        adapter = StudentAdapter(this, studentList) { student ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("studentId", student.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Thêm dữ liệu mẫu nếu cần
        activityScope.launch {
            withContext(Dispatchers.IO) {
                    addSampleData()
            }
            loadStudents() // Load dữ liệu sinh viên sau khi thêm dữ liệu mẫu
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                searchStudents(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_delete -> {
                deleteSelectedStudents() // Gọi hàm xóa nhiều sinh viên
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Hàm xóa các sinh viên đã chọn
    private fun deleteSelectedStudents() {
        CoroutineScope(Dispatchers.Main).launch {
            val selectedStudents = adapter.getSelectedStudents()
            if (selectedStudents.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    appDatabase.studentDao().deleteStudents(selectedStudents) // Xóa trong cơ sở dữ liệu
                }
                loadStudents() // Cập nhật lại danh sách
            } else {
                Toast.makeText(this@MainActivity, "Không có sinh viên nào được chọn!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents() // Làm mới danh sách sinh viên mỗi khi quay lại màn hình chính
    }

    private fun loadStudents() {
        activityScope.launch {
            val students = withContext(Dispatchers.IO) {
                appDatabase.studentDao().getAllStudents()
            }
            studentList.clear()
            studentList.addAll(students)
            adapter.notifyDataSetChanged()
        }
    }

    private fun searchStudents(keyword: String) {
        activityScope.launch {
            val students = withContext(Dispatchers.IO) {
                appDatabase.studentDao().searchStudents("%$keyword%")
            }
            studentList.clear()
            studentList.addAll(students)
            adapter.notifyDataSetChanged()
        }
    }

    private fun addSampleData() {
        val sampleStudents = listOf(
            Student(mssv = "SV001", hoten = "Nguyen Van A", ngaysinh = "2000-01-01", email = "a@example.com"),
            Student(mssv = "SV002", hoten = "Tran Thi B", ngaysinh = "2000-02-02", email = "b@example.com"),
            Student(mssv = "SV003", hoten = "Le Van C", ngaysinh = "2000-03-03", email = "c@example.com"),
            Student(mssv = "SV004", hoten = "Pham Thi D", ngaysinh = "2000-04-04", email = "d@example.com"),
            Student(mssv = "SV005", hoten = "Hoang Van E", ngaysinh = "2000-05-05", email = "e@example.com"),
            Student(mssv = "SV006", hoten = "Nguyen Van F", ngaysinh = "2000-06-06", email = "f@example.com"),
            Student(mssv = "SV007", hoten = "Tran Thi G", ngaysinh = "2000-07-07", email = "g@example.com"),
            Student(mssv = "SV008", hoten = "Le Van H", ngaysinh = "2000-08-08", email = "h@example.com"),
            Student(mssv = "SV009", hoten = "Pham Thi I", ngaysinh = "2000-09-09", email = "i@example.com"),
            Student(mssv = "SV010", hoten = "Hoang Van J", ngaysinh = "2000-10-10", email = "j@example.com"),
            // Thêm các sinh viên khác...
        )
        appDatabase.studentDao().insertAll(sampleStudents)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel() // Hủy phạm vi hoạt động khi Activity bị hủy
    }
}
