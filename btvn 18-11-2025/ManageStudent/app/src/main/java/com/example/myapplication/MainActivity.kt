package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtStudentId: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var lvStudents: ListView
    
    private val students = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        edtName = findViewById(R.id.edtName)
        edtStudentId = findViewById(R.id.edtStudentId)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        lvStudents = findViewById(R.id.lvStudents)
        
        // Initialize adapter
        adapter = StudentAdapter(this, students) { position ->
            deleteStudent(position)
        }
        lvStudents.adapter = adapter
        
        // Add button click listener
        btnAdd.setOnClickListener {
            addStudent()
        }
        
        // Update button click listener
        btnUpdate.setOnClickListener {
            updateStudent()
        }
        
        // List item click listener
        lvStudents.setOnItemClickListener { parent, view, position, id ->
            selectStudent(position)
            Toast.makeText(this, "Đã chọn sinh viên", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun addStudent() {
        val name = edtName.text.toString().trim()
        val studentId = edtStudentId.text.toString().trim()
        
        if (name.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }
        
        val student = Student(name, studentId)
        students.add(student)
        adapter.notifyDataSetChanged()
        
        clearInputs()
        Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateStudent() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            return
        }
        
        val name = edtName.text.toString().trim()
        val studentId = edtStudentId.text.toString().trim()
        
        if (name.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }
        
        students[selectedPosition] = Student(name, studentId)
        adapter.notifyDataSetChanged()
        
        clearInputs()
        selectedPosition = -1
        btnUpdate.isEnabled = false
        Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show()
    }
    
    private fun deleteStudent(position: Int) {
        students.removeAt(position)
        adapter.notifyDataSetChanged()
        
        if (position == selectedPosition) {
            clearInputs()
            selectedPosition = -1
            btnUpdate.isEnabled = false
        }
        
        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
    }
    
    private fun selectStudent(position: Int) {
        selectedPosition = position
        val student = students[position]
        
        edtName.setText(student.name)
        edtStudentId.setText(student.studentId)
        btnUpdate.isEnabled = true
    }
    
    private fun clearInputs() {
        edtName.text.clear()
        edtStudentId.text.clear()
    }
}