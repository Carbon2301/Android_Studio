package com.example.number_filter_app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNumber: EditText
    private lateinit var rbEven: RadioButton
    private lateinit var rbOdd: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var btnShow: Button
    private lateinit var lvNumbers: ListView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumber = findViewById(R.id.etNumber)
        rbEven = findViewById(R.id.rbEven)
        rbOdd = findViewById(R.id.rbOdd)
        rbSquare = findViewById(R.id.rbSquare)
        btnShow = findViewById(R.id.btnShow)
        lvNumbers = findViewById(R.id.lvNumbers)
        tvError = findViewById(R.id.tvError)

        btnShow.setOnClickListener {
            tvError.text = ""
            val n = etNumber.text.toString().toIntOrNull()

            if (n == null || n < 0) {
                tvError.text = "Vui lòng nhập một số nguyên dương."
                return@setOnClickListener
            }

            val numbersList = when {
                rbEven.isChecked -> generateEvenNumbers(n)
                rbOdd.isChecked -> generateOddNumbers(n)
                rbSquare.isChecked -> generateSquareNumbers(n)
                else -> emptyList()
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
            lvNumbers.adapter = adapter
        }
    }

    private fun generateEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun generateOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun generateSquareNumbers(n: Int): List<Int> {
        val squareNumbers = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            squareNumbers.add(i * i)
            i++
        }
        return squareNumbers
    }
}
