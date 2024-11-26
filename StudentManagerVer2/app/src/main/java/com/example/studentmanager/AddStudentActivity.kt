package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddStudentActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextMSSV: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        editTextName = findViewById(R.id.editTextName)
        editTextMSSV = findViewById(R.id.editTextMSSV)

        // Nhận dữ liệu (nếu có) từ Intent
        val name = intent.getStringExtra("name")
        val mssv = intent.getStringExtra("mssv")
        val position = intent.getIntExtra("position", -1)

        if (name != null) editTextName.setText(name)
        if (mssv != null) editTextMSSV.setText(mssv)

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val intent = Intent()
            intent.putExtra("name", editTextName.text.toString())
            intent.putExtra("mssv", editTextMSSV.text.toString())
            if (position >= 0) intent.putExtra("position", position)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}