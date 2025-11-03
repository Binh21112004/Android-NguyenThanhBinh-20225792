package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // Views
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etBirthday: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var calendarView: CalendarView
    private lateinit var calendarCard: View
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnRegister: Button


    private val COLOR_NORMAL by lazy { Color.parseColor("#E0E0E0") }
    private val COLOR_ERROR  by lazy { Color.parseColor("#FFCDD2") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindViews()
        setupCalendarBehavior()
        setupRegisterValidation()
    }

    private fun bindViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName  = findViewById(R.id.etLastName)
        rgGender    = findViewById(R.id.rgGender)
        etBirthday  = findViewById(R.id.etBirthday)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        calendarView  = findViewById(R.id.calendarView)
        calendarCard  = findViewById(R.id.calendarCard)
        etAddress   = findViewById(R.id.etAddress)
        etEmail     = findViewById(R.id.etEmail)
        cbTerms     = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegister)
    }

    /** 1) Nhấn Select để hiện/ẩn lịch. Chọn ngày -> đổ vào EditText, ẩn lịch */
    private fun setupCalendarBehavior() {

        btnSelectDate.setOnClickListener {
            if (calendarCard.visibility == View.VISIBLE) {
                calendarCard.visibility = View.GONE
            } else {
                calendarCard.visibility = View.VISIBLE
            }
        }


        calendarView.setOnDateChangeListener { _, year, month, day ->
            val cal = Calendar.getInstance().apply {
                set(year, month, day)
            }
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            etBirthday.setText(sdf.format(cal.time))

            calendarCard.visibility = View.GONE
        }


        etBirthday.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
            }
        }
    }

    /** 2) Nhấn Register -> kiểm tra bắt buộc, tô nền đỏ nhạt ô nào còn thiếu */
    private fun setupRegisterValidation() {
        btnRegister.setOnClickListener {
            resetErrorBackgrounds()

            var ok = true

            if (etFirstName.text.isNullOrBlank()) {
                markError(etFirstName); ok = false
            }
            if (etLastName.text.isNullOrBlank()) {
                markError(etLastName); ok = false
            }
            if (rgGender.checkedRadioButtonId == -1) {

                rgGender.setBackgroundColor(COLOR_ERROR); ok = false
            }
            if (etBirthday.text.isNullOrBlank()) {
                markError(etBirthday); ok = false
            }
            if (etAddress.text.isNullOrBlank()) {
                markError(etAddress); ok = false
            }
            if (etEmail.text.isNullOrBlank()) {
                markError(etEmail); ok = false
            }
            if (!cbTerms.isChecked) {

                cbTerms.setBackgroundColor(COLOR_ERROR); ok = false
            }

            if (ok) {
                Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun markError(view: View) {
        when (view) {
            is EditText -> view.setBackgroundColor(COLOR_ERROR)
            else -> view.setBackgroundColor(COLOR_ERROR)
        }
    }

    private fun resetErrorBackgrounds() {

        listOf(etFirstName, etLastName, etBirthday, etAddress, etEmail).forEach {
            it.setBackgroundColor(COLOR_NORMAL)
        }

        rgGender.setBackgroundColor(Color.TRANSPARENT)
        cbTerms.setBackgroundColor(Color.TRANSPARENT)
    }
}
