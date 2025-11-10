package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var etFromAmount: EditText
    private lateinit var etToAmount: EditText
    private lateinit var tvExchangeRate: TextView
    
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "JPY" to 149.50,
        "VND" to 24500.0,
        "CNY" to 7.24,
        "KRW" to 1320.0,
        "THB" to 35.50,
        "SGD" to 1.35,
        "AUD" to 1.53
    )
    
    private var isUpdating = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initViews()
        setupSpinners()
        setupTextWatchers()
    }
    
    private fun initViews() {
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        etFromAmount = findViewById(R.id.etFromAmount)
        etToAmount = findViewById(R.id.etToAmount)
        tvExchangeRate = findViewById(R.id.tvExchangeRate)
    }
    
    private fun setupSpinners() {
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter
        
        spinnerFromCurrency.setSelection(0)
        spinnerToCurrency.setSelection(4)
        
        spinnerFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertFromToTo()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        spinnerToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertFromToTo()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun setupTextWatchers() {
        etFromAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && etFromAmount.hasFocus()) {
                    convertFromToTo()
                }
            }
        })
        
        etToAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && etToAmount.hasFocus()) {
                    convertToToFrom()
                }
            }
        })
    }
    
    private fun convertFromToTo() {
        val fromCurrencyCode = getCurrencyCode(spinnerFromCurrency.selectedItem.toString())
        val toCurrencyCode = getCurrencyCode(spinnerToCurrency.selectedItem.toString())
        val amountStr = etFromAmount.text.toString()
        
        if (amountStr.isEmpty() || amountStr == ".") {
            isUpdating = true
            etToAmount.setText("")
            isUpdating = false
            updateExchangeRateDisplay(fromCurrencyCode, toCurrencyCode)
            return
        }
        
        try {
            val amount = amountStr.toDouble()
            val fromRate = exchangeRates[fromCurrencyCode] ?: 1.0
            val toRate = exchangeRates[toCurrencyCode] ?: 1.0
            
            val amountInUSD = amount / fromRate
            val convertedAmount = amountInUSD * toRate
            
            isUpdating = true
            etToAmount.setText(formatAmount(convertedAmount))
            isUpdating = false
            
            updateExchangeRateDisplay(fromCurrencyCode, toCurrencyCode)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }
    
    private fun convertToToFrom() {
        val fromCurrencyCode = getCurrencyCode(spinnerFromCurrency.selectedItem.toString())
        val toCurrencyCode = getCurrencyCode(spinnerToCurrency.selectedItem.toString())
        val amountStr = etToAmount.text.toString()
        
        if (amountStr.isEmpty() || amountStr == ".") {
            isUpdating = true
            etFromAmount.setText("")
            isUpdating = false
            updateExchangeRateDisplay(fromCurrencyCode, toCurrencyCode)
            return
        }
        
        try {
            val amount = amountStr.toDouble()
            val fromRate = exchangeRates[fromCurrencyCode] ?: 1.0
            val toRate = exchangeRates[toCurrencyCode] ?: 1.0
            
            val amountInUSD = amount / toRate
            val convertedAmount = amountInUSD * fromRate
            
            isUpdating = true
            etFromAmount.setText(formatAmount(convertedAmount))
            isUpdating = false
            
            updateExchangeRateDisplay(fromCurrencyCode, toCurrencyCode)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }
    
    private fun getCurrencyCode(currencyString: String): String {
        return currencyString.split(" - ")[0]
    }
    
    private fun formatAmount(amount: Double): String {
        val df = DecimalFormat("#.##")
        return df.format(amount)
    }
    
    private fun updateExchangeRateDisplay(fromCode: String, toCode: String) {
        val fromRate = exchangeRates[fromCode] ?: 1.0
        val toRate = exchangeRates[toCode] ?: 1.0
        val rate = toRate / fromRate
        
        val df = DecimalFormat("#.####")
        tvExchangeRate.text = "Exchange rate: 1 $fromCode = ${df.format(rate)} $toCode"
    }
}