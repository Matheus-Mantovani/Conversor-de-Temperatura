package br.edu.ifsp.dmo.conversordetemperatura.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.conversordetemperatura.R
import br.edu.ifsp.dmo.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.conversordetemperatura.model.CelsiusStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.ConversorTemperatura
import br.edu.ifsp.dmo.conversordetemperatura.model.FahrenheitStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.KelvinStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var converterStrategy: ConversorTemperatura
    private var temperaturaOrigem: ConversorTemperatura = CelsiusStrategy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
        setSpinnerListener()
    }

    private fun setClickListener() {
        binding.btnCelsius.setOnClickListener {
            handleConversion(CelsiusStrategy)
        }

        binding.btnFahrenheit.setOnClickListener {
            handleConversion(FahrenheitStrategy)
        }

        binding.btnKelvin.setOnClickListener {
            handleConversion(KelvinStrategy)
        }
    }

    private fun setSpinnerListener() {
        setSpinner()

        binding.spinnerStrategies.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                temperaturaOrigem = when (position) {
                    0 -> CelsiusStrategy
                    1 -> FahrenheitStrategy
                    else -> KelvinStrategy
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                temperaturaOrigem = CelsiusStrategy
            }
        }
    }

    private fun readTemperature(): Double {
        return try {
            binding.edittextTemperature.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            throw NumberFormatException("Input error")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun handleConversion(strategy: ConversorTemperatura) {
        converterStrategy = strategy

        try {
            val inputValue = readTemperature()
            val result = converterStrategy.converter(inputValue, temperaturaOrigem)
            binding.textviewResultNumber.text = String.format(
                "%.2f %s",
                result,
                converterStrategy.getScale()
            )
            binding.textviewResultMessage.text = when(temperaturaOrigem) {
                is CelsiusStrategy -> when(strategy) {
                    is FahrenheitStrategy -> getString(R.string.msgCtoF)
                    is KelvinStrategy -> getString(R.string.msgCtoK)
                    else -> ""
                }
                is FahrenheitStrategy -> when(strategy) {
                    is CelsiusStrategy -> getString(R.string.msgFtoC)
                    is KelvinStrategy -> getString(R.string.msgFtoK)
                    else -> ""
                }
                is KelvinStrategy -> when(strategy) {
                    is CelsiusStrategy -> getString(R.string.msgKtoC)
                    is FahrenheitStrategy -> getString(R.string.msgKtoF)
                    else -> ""
                }
                else -> ""
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_popup_notify),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("APP_DMO", e.stackTraceToString())
        }
    }

    private fun setSpinner() {
        val options = listOf(
            getString(R.string.celsius),
            getString(R.string.fahrenheit),
            getString(R.string.kelvin)
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerStrategies.adapter = adapter
    }
}