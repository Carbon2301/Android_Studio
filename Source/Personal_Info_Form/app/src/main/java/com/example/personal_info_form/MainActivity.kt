package com.example.personal_info_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edtMSSV: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var edtEmail: EditText
    private lateinit var edtPhone: EditText
    private lateinit var btnDateOfBirth: Button
    private lateinit var txtDateOfBirth: TextView
    private lateinit var spinnerWard: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var cbSport: CheckBox
    private lateinit var cbMovie: CheckBox
    private lateinit var cbMusic: CheckBox
    private lateinit var cbAgree: CheckBox
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các view
        edtMSSV = findViewById(R.id.edtMSSV)
        edtHoTen = findViewById(R.id.edtHoTen)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        edtEmail = findViewById(R.id.edtEmail)
        edtPhone = findViewById(R.id.edtPhone)
        btnDateOfBirth = findViewById(R.id.btnDateOfBirth)
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth)
        spinnerWard = findViewById(R.id.spinnerWard)
        spinnerDistrict = findViewById(R.id.spinnerDistrict)
        spinnerCity = findViewById(R.id.spinnerCity)
        cbSport = findViewById(R.id.cbSport)
        cbMovie = findViewById(R.id.cbMovie)
        cbMusic = findViewById(R.id.cbMusic)
        cbAgree = findViewById(R.id.cbAgree)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Set up Date Picker Dialog
        btnDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }

        // Xử lý khi nhấn nút Submit
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(this, "Thông tin đã được gửi thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm hiện dialog chọn ngày
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                txtDateOfBirth.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    // Hàm kiểm tra tính hợp lệ của form
    private fun validateForm(): Boolean {
        var isValid = true

        if (edtMSSV.text.isBlank()) {
            edtMSSV.error = "Vui lòng nhập MSSV"
            isValid = false
        }

        if (edtHoTen.text.isBlank()) {
            edtHoTen.error = "Vui lòng nhập họ tên"
            isValid = false
        }

        if (radioGroupGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (edtEmail.text.isBlank()) {
            edtEmail.error = "Vui lòng nhập email"
            isValid = false
        }

        if (edtPhone.text.isBlank()) {
            edtPhone.error = "Vui lòng nhập số điện thoại"
            isValid = false
        }

        if (txtDateOfBirth.text.isBlank()) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (spinnerWard.selectedItem == null || spinnerDistrict.selectedItem == null || spinnerCity.selectedItem == null) {
            Toast.makeText(this, "Vui lòng chọn nơi ở hiện tại", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!cbAgree.isChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với các điều khoản", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}
