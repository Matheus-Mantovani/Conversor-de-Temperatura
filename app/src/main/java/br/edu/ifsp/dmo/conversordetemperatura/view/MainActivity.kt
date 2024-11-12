package br.edu.ifsp.dmo.conversordetemperatura.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.conversordetemperatura.R
import br.edu.ifsp.dmo.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.conversordetemperatura.model.CelsiusStrategy
import br.edu.ifsp.dmo.conversordetemperatura.model.ConversorTemperatura
import br.edu.ifsp.dmo.conversordetemperatura.model.FahrenheitStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var converterStrategy: ConversorTemperatura

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCelsius.setOnClickListener {
            handleConversion(CelsiusStrategy)
        }

        binding.btnFahrenheit.setOnClickListener {
            handleConversion(FahrenheitStrategy)
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
            binding.textviewResultNumber.text = String.format(
                "%.2f %s",
                converterStrategy.converter(inputValue),
                converterStrategy.getScale()
            )
            binding.textviewResultMessage.text = if (this.converterStrategy is CelsiusStrategy) {
                getString(R.string.msgCtoF)
            } else {
                getString(R.string.msgFtoC)
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
}