package com.example.calculator_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var result: TextView // Khai báo TextView cho kết quả
    private var currentExpression = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đảm bảo tên layout đúng

        display = findViewById(R.id.display)
        result = findViewById(R.id.result) // Ánh xạ TextView cho kết quả

        // Tạo mảng các nút số và phép toán
        val buttonIds = intArrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonDot
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                currentExpression.append(button.text)
                display.text = currentExpression.toString()
                updateResult() // Cập nhật kết quả mỗi khi nhấn nút
            }
        }

        // Nút CE - Clear All
        findViewById<Button>(R.id.buttonCE).setOnClickListener {
            currentExpression.clear()
            display.text = "0"
            result.text = "" // Xóa kết quả khi xóa biểu thức
        }

        // Nút C - Clear Memory
        findViewById<Button>(R.id.buttonC).setOnClickListener {
            currentExpression.clear()
            display.text = "0" // Đặt lại hiển thị
            result.text = "" // Xóa kết quả
        }

        // Nút BS - Backspace
        findViewById<Button>(R.id.buttonBS).setOnClickListener {
            if (currentExpression.isNotEmpty()) {
                currentExpression.deleteCharAt(currentExpression.length - 1)
                display.text = if (currentExpression.isEmpty()) "0" else currentExpression.toString()
                updateResult() // Cập nhật kết quả khi xóa ký tự
            }
        }

        // Nút ^ - Tính lũy thừa
        findViewById<Button>(R.id.buttonExponent).setOnClickListener {
            currentExpression.append("^") // Thêm dấu ^ vào biểu thức
            display.text = currentExpression.toString() // Cập nhật hiển thị
            updateResult() // Cập nhật kết quả
        }

        // Nút = - Tính toán kết quả
        findViewById<Button>(R.id.buttonEqual).setOnClickListener {
            try {
                val expression: Expression = ExpressionBuilder(currentExpression.toString()).build()
                val resultValue = expression.evaluate()

                // Kiểm tra xem kết quả có phải là số nguyên hay không
                display.text = if (resultValue == resultValue.toInt().toDouble()) {
                    resultValue.toInt().toString() // Chỉ hiển thị phần nguyên
                } else {
                    resultValue.toString()
                }

                currentExpression = StringBuilder(display.text.toString())
            } catch (e: Exception) {
                display.text = "Error"
                currentExpression.clear()
            }
        }
    }

    // Hàm cập nhật kết quả
    private fun updateResult() {
        try {
            // Chỉ cần xây dựng biểu thức từ currentExpression
            val expression: Expression = ExpressionBuilder(currentExpression.toString()).build()
            val resultValue = expression.evaluate()

            // Cập nhật kết quả
            result.text = if (resultValue == resultValue.toInt().toDouble()) {
                resultValue.toInt().toString() // Chỉ hiển thị phần nguyên
            } else {
                resultValue.toString()
            }
        } catch (e: Exception) {
            result.text = "" // Xóa kết quả khi có lỗi
        }
    }
}
