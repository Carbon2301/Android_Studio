package com.example.currency_converter_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSource: EditText
    private lateinit var etDestination: EditText
    private lateinit var spSourceCurrency: Spinner
    private lateinit var spDestinationCurrency: Spinner

    private var isSourceEditing = true

    // Tỉ giá hối đoái với VND bổ sung
    private val exchangeRates = mapOf(
        "AUD" to 1.5073102244,
        "BGN" to 1.8173203445,
        "BRL" to 5.6864007021,
        "CAD" to 1.383700259,
        "CHF" to 0.8667401633,
        "CNY" to 7.1308312405,
        "CZK" to 23.4180336881,
        "DKK" to 6.9186009789,
        "EUR" to 0.9275401454,
        "GBP" to 0.7744301036,
        "HKD" to 7.7671608009,
        "HRK" to 6.5559409276,
        "HUF" to 374.020970336,
        "IDR" to 15610.989424789,
        "ILS" to 3.8002003881,
        "INR" to 84.0265884776,
        "ISK" to 138.7618584246,
        "JPY" to 152.7527367993,
        "KRW" to 1378.2557040663,
        "MXN" to 19.8241332922,
        "MYR" to 4.3507607681,
        "NOK" to 10.9872518411,
        "NZD" to 1.6652501706,
        "PHP" to 58.1272782698,
        "PLN" to 4.0287406134,
        "RON" to 4.6131107667,
        "RUB" to 96.0202130735,
        "SEK" to 10.5980216877,
        "SGD" to 1.3228802186,
        "THB" to 33.7676141576,
        "TRY" to 34.2500051828,
        "USD" to 1.0,
        "ZAR" to 17.7822634988,
        "VND" to 24300.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSource = findViewById(R.id.etSource)
        etDestination = findViewById(R.id.etDestination)
        spSourceCurrency = findViewById(R.id.spSourceCurrency)
        spDestinationCurrency = findViewById(R.id.spDestinationCurrency)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spSourceCurrency.adapter = adapter
        spDestinationCurrency.adapter = adapter

        etSource.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isSourceEditing) {
                    if (s.isNullOrEmpty()) {
                        etDestination.text.clear()
                    } else {
                        convertCurrency(true)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isSourceEditing) {
                    if (s.isNullOrEmpty()) {
                        etSource.text.clear()
                    } else {
                        convertCurrency(false)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etSource.setOnFocusChangeListener { _, hasFocus ->
            isSourceEditing = hasFocus
        }

        etDestination.setOnFocusChangeListener { _, hasFocus ->
            isSourceEditing = !hasFocus
        }

        spSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency(true)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spDestinationCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!etDestination.text.isNullOrEmpty()) {
                    convertDestinationCurrency()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency(isSource: Boolean) {
        val sourceAmount: Double
        val sourceCurrency: String
        val destinationCurrency: String

        if (isSource) {
            sourceAmount = etSource.text.toString().toDoubleOrNull() ?: return
            sourceCurrency = spSourceCurrency.selectedItem.toString()
            destinationCurrency = spDestinationCurrency.selectedItem.toString()
        } else {
            sourceAmount = etDestination.text.toString().toDoubleOrNull() ?: return
            sourceCurrency = spDestinationCurrency.selectedItem.toString()
            destinationCurrency = spSourceCurrency.selectedItem.toString()
        }

        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val destinationRate = exchangeRates[destinationCurrency] ?: 1.0

        val amountInUSD = sourceAmount / sourceRate
        val convertedAmount = amountInUSD * destinationRate

        if (isSource) {
            etDestination.setText(String.format("%.2f", convertedAmount))
        } else {
            etSource.setText(String.format("%.2f", convertedAmount))
        }
    }

    private fun convertDestinationCurrency() {
        val destinationAmount = etDestination.text.toString().toDoubleOrNull() ?: return
        val sourceCurrency = spSourceCurrency.selectedItem.toString()
        val destinationCurrency = spDestinationCurrency.selectedItem.toString()

        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val destinationRate = exchangeRates[destinationCurrency] ?: 1.0

        val amountInUSD = destinationAmount / destinationRate
        val convertedAmount = amountInUSD * sourceRate

        etSource.setText(String.format("%.2f", convertedAmount))
    }
}
