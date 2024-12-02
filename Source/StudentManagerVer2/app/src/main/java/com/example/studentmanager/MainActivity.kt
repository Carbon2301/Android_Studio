package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val studentList = mutableListOf<Pair<String, String>>(
        "Nguyen Van An" to "2022",
        "Tran Thi Binh" to "2023",
        "Le Van Cao" to "2024",
        "Pham Thi Dong" to "2025",
        "Hoang Van En" to "2026",
        "Dang Thi Huong" to "2027",
        "Vu Van Gian" to "2028",
        "Nguyen Thi Hong" to "2029",
        "Tran Van Thong" to "2030",
        "Le Thi Hoa" to "2031",
        "Pham Van Khai" to "2032",
        "Hoang Thi Lan" to "2033",
        "Dang Van Minh" to "2034",
        "Vu Thi Ninh" to "2035",
        "Nguyen Van Son" to "2036",
        "Tran Thi Phuong" to "2037",
        "Le Van Quyen" to "2038",
        "Pham Thi Binh" to "2039",
        "Hoang Van Son" to "2040",
        "Dang Thi Tu" to "2041"
    )
    // Lưu danh sách (Họ tên, MSSV)
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)

        // Adapter hiển thị danh sách sinh viên
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList.map { "${it.first} - ${it.second}" })
        listView.adapter = adapter

        // Đăng ký context menu
        registerForContextMenu(listView)
    }

    // Tạo OptionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Xử lý OptionMenu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                // Mở Activity thêm sinh viên
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Tạo ContextMenu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý ContextMenu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.action_edit -> {
                // Mở Activity chỉnh sửa
                val intent = Intent(this, AddStudentActivity::class.java)
                intent.putExtra("name", studentList[info.position].first)
                intent.putExtra("mssv", studentList[info.position].second)
                intent.putExtra("position", info.position)
                startActivityForResult(intent, REQUEST_EDIT)
            }
            R.id.action_remove -> {
                // Xóa sinh viên
                studentList.removeAt(info.position)
                updateList()
            }
        }
        return super.onContextItemSelected(item)
    }

    // Nhận kết quả từ Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("name") ?: return
            val mssv = data.getStringExtra("mssv") ?: return

            when (requestCode) {
                REQUEST_ADD -> {
                    studentList.add(Pair(name, mssv))
                }
                REQUEST_EDIT -> {
                    val position = data.getIntExtra("position", -1)
                    if (position >= 0) {
                        studentList[position] = Pair(name, mssv)
                    }
                }
            }
            updateList()
        }
    }

    private fun updateList() {
        adapter.clear()
        adapter.addAll(studentList.map { "${it.first} - ${it.second}" })
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }
}
