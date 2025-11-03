package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var expr: TextView

    private var current = "0"         // đang nhập, dạng chuỗi (có thể có '.')
    private var left: Double? = null  // đổi sang Double để tính số thực
    private var op: Operator? = null
    private var justEvaluated = false

    private enum class Operator { ADD, SUB, MUL, DIV }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)
        expr = findViewById(R.id.tvExpr)

        // gán listener cho nút số
        val digitIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val d = (it as Button).text.first()
                appendDigit(d)
            }
        }

        // phép toán
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator(Operator.ADD) }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator(Operator.SUB) }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator(Operator.MUL) }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator(Operator.DIV) }

        // chức năng
        findViewById<Button>(R.id.btnEq).setOnClickListener { evaluate() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnNeg).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { appendDot() } // giờ dùng được

        updateDisplay()
    }

    // ---------- Helpers ----------
    private fun opSymbol(o: Operator) = when (o) {
        Operator.ADD -> "+"
        Operator.SUB -> "-"
        Operator.MUL -> "×"
        Operator.DIV -> "÷"
    }

    private fun parseCurrent(): Double? = current.toDoubleOrNull()

    private fun formatNumber(x: Double): String {
        // Hiển thị gọn: nếu là số nguyên -> bỏ .0, nếu số thực -> cắt đuôi 0 và dấu chấm thừa
        val asLong = x.toLong()
        return if (x.isFinite().not()) x.toString()
        else if (x % 1.0 == 0.0) asLong.toString()
        else x.toString().trimEnd('0').trimEnd('.')
    }

    private fun updateExpressionLabel() {
        val l = left
        val operator = op

        val text = when {
            justEvaluated -> expr.text.toString()
            l != null && operator != null && (current == "0" || current == "-0") -> {
                "${formatNumber(l)} ${opSymbol(operator)}"
            }
            l != null && operator != null -> {
                // đang nhập toán hạng phải
                "${formatNumber(l)} ${opSymbol(operator)} $current"
            }
            else -> ""
        }
        expr.text = text
    }

    private fun updateDisplay() {
        // nếu current là số thực hợp lệ, có thể format nhẹ để nhìn gọn hơn
        val v = current.toDoubleOrNull()
        display.text = if (v != null && !current.endsWith(".")) formatNumber(v) else current
        updateExpressionLabel()
    }

    // ---------- Logic xử lý ----------
    private fun appendDigit(d: Char) {
        if (justEvaluated) {
            left = null
            op = null
            current = "0"
            justEvaluated = false
            expr.text = ""
        }
        current = when {
            current == "0" -> d.toString()
            current == "-0" -> "-$d"
            current.length < 20 -> current + d
            else -> current
        }
        updateDisplay()
    }

    private fun appendDot() {
        if (justEvaluated) {
            // sau "=" mà bấm ".", bắt phép tính mới với "0."
            left = null
            op = null
            current = "0"
            justEvaluated = false
            expr.text = ""
        }
        if (!current.contains('.')) {
            current += if (current == "" || current == "-" ) "0." else "."
        }
        updateDisplay()
    }

    private fun setOperator(newOp: Operator) {
        if (op == null) {
            left = parseCurrent() ?: 0.0
            current = "0"
        } else {
            // tính dồn giống Windows
            evaluate()
            left = parseCurrent() ?: 0.0
            current = "0"
            justEvaluated = false
        }
        op = newOp
        updateDisplay()
    }

    private fun evaluate() {
        val l = left
        val r = parseCurrent()
        val operator = op

        if (l == null || r == null || operator == null) {
            updateDisplay()
            return
        }

        val result: Double? = when (operator) {
            Operator.ADD -> l + r
            Operator.SUB -> l - r
            Operator.MUL -> l * r
            Operator.DIV -> if (r == 0.0) null else l / r  // CHIA SỐ THỰC
        }

        if (result == null || result.isNaN() || result.isInfinite()) {
            showError("Cannot divide by zero")
            return
        }

        // Dòng biểu thức theo kiểu Windows: "l op r ="
        expr.text = "${formatNumber(l)} ${opSymbol(operator)} ${formatNumber(r)} ="

        current = formatNumber(result)
        left = null
        op = null
        justEvaluated = true
        display.text = current
        // giữ nguyên "... =" trên expr
    }

    // C
    private fun clearAll() {
        current = "0"
        left = null
        op = null
        justEvaluated = false
        expr.text = ""
        updateDisplay()
    }

    // CE
    private fun clearEntry() {
        current = "0"
        if (!justEvaluated) updateExpressionLabel() else expr.text = ""
        justEvaluated = false
        display.text = current
    }

    // BS
    private fun backspace() {
        if (justEvaluated) {
            clearEntry()
            return
        }
        current = when {
            current.length <= 1 || (current.startsWith('-') && current.length == 2) -> "0"
            else -> current.dropLast(1)
        }
        // tránh kết thúc bằng "-", "." đơn lẻ
        if (current == "-" || current == "." || current == "-.") current = "0"
        updateDisplay()
    }

    // +/-
    private fun toggleSign() {
        if (current == "0" || current == "0.") {
            current = "-0"
        } else {
            current = if (current.startsWith("-")) current.drop(1) else "-$current"
        }
        updateDisplay()
    }

    private fun showError(msg: String) {
        display.text = msg
        // reset để nhập lại từ đầu
        current = "0"
        left = null
        op = null
        justEvaluated = false
        expr.text = ""
    }
}
