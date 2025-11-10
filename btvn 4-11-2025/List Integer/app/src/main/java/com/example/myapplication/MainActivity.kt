package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    
    private lateinit var etNumber: EditText
    private lateinit var lvNumbers: ListView
    private lateinit var tvEmptyMessage: TextView
    
    private lateinit var rbOdd: RadioButton
    private lateinit var rbPrime: RadioButton
    private lateinit var rbPerfect: RadioButton
    private lateinit var rbEven: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var rbFibonacci: RadioButton
    
    private var adapter: ArrayAdapter<Int>? = null
    private var selectedRadioButton: RadioButton? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupListeners()
        updateNumberList()
    }
    
    private fun initViews() {
        etNumber = findViewById(R.id.etNumber)
        lvNumbers = findViewById(R.id.lvNumbers)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        
        rbOdd = findViewById(R.id.rbOdd)
        rbPrime = findViewById(R.id.rbPrime)
        rbPerfect = findViewById(R.id.rbPerfect)
        rbEven = findViewById(R.id.rbEven)
        rbSquare = findViewById(R.id.rbSquare)
        rbFibonacci = findViewById(R.id.rbFibonacci)
        
        // Set default selection
        selectedRadioButton = rbOdd
        rbOdd.isChecked = true
    }
    
    private fun setupListeners() {
        // Listen for text changes in EditText
        etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateNumberList()
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
        
        // Set up RadioButton click listeners
        val radioButtons = listOf(rbOdd, rbPrime, rbPerfect, rbEven, rbSquare, rbFibonacci)
        
        for (rb in radioButtons) {
            rb.setOnClickListener {
                // Uncheck all other radio buttons
                radioButtons.forEach { it.isChecked = false }
                // Check only the clicked one
                rb.isChecked = true
                selectedRadioButton = rb
                updateNumberList()
            }
        }
    }
    
    private fun updateNumberList() {
        val inputText = etNumber.text.toString()
        
        if (inputText.isEmpty()) {
            showEmptyMessage()
            return
        }
        
        val limit = inputText.toIntOrNull()
        if (limit == null || limit <= 0) {
            showEmptyMessage()
            return
        }
        
        val numbers = when (selectedRadioButton) {
            rbOdd -> getOddNumbers(limit)
            rbEven -> getEvenNumbers(limit)
            rbPrime -> getPrimeNumbers(limit)
            rbPerfect -> getPerfectNumbers(limit)
            rbSquare -> getSquareNumbers(limit)
            rbFibonacci -> getFibonacciNumbers(limit)
            else -> emptyList()
        }
        
        if (numbers.isEmpty()) {
            showEmptyMessage()
        } else {
            showNumberList(numbers)
        }
    }
    
    private fun showEmptyMessage() {
        lvNumbers.visibility = View.GONE
        tvEmptyMessage.visibility = View.VISIBLE
    }
    
    private fun showNumberList(numbers: List<Int>) {
        tvEmptyMessage.visibility = View.GONE
        lvNumbers.visibility = View.VISIBLE
        
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            numbers
        )
        lvNumbers.adapter = adapter
    }
    
    // Generate odd numbers less than limit
    private fun getOddNumbers(limit: Int): List<Int> {
        return (0 until limit).filter { it % 2 != 0 }
    }
    
    // Generate even numbers less than limit
    private fun getEvenNumbers(limit: Int): List<Int> {
        return (0 until limit).filter { it % 2 == 0 }
    }
    
    // Generate prime numbers less than limit
    private fun getPrimeNumbers(limit: Int): List<Int> {
        if (limit <= 2) return emptyList()
        
        val primes = mutableListOf<Int>()
        for (num in 2 until limit) {
            if (isPrime(num)) {
                primes.add(num)
            }
        }
        return primes
    }
    
    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        if (n == 2) return true
        if (n % 2 == 0) return false
        
        val sqrtN = sqrt(n.toDouble()).toInt()
        for (i in 3..sqrtN step 2) {
            if (n % i == 0) return false
        }
        return true
    }
    
    // Generate perfect numbers less than limit
    private fun getPerfectNumbers(limit: Int): List<Int> {
        val perfectNumbers = mutableListOf<Int>()
        for (num in 2 until limit) {
            if (isPerfect(num)) {
                perfectNumbers.add(num)
            }
        }
        return perfectNumbers
    }
    
    private fun isPerfect(n: Int): Boolean {
        if (n < 2) return false
        
        var sum = 1
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 2..sqrtN) {
            if (n % i == 0) {
                sum += i
                if (i != n / i) {
                    sum += n / i
                }
            }
        }
        return sum == n
    }
    
    // Generate perfect square numbers less than limit
    private fun getSquareNumbers(limit: Int): List<Int> {
        val squares = mutableListOf<Int>()
        var i = 1
        while (i * i < limit) {
            squares.add(i * i)
            i++
        }
        return squares
    }
    
    // Generate Fibonacci numbers less than limit
    private fun getFibonacciNumbers(limit: Int): List<Int> {
        if (limit <= 1) return emptyList()
        
        val fibonacci = mutableListOf<Int>()
        var a = 1
        var b = 1
        
        while (a < limit) {
            fibonacci.add(a)
            val temp = a + b
            a = b
            b = temp
        }
        
        return fibonacci
    }
}