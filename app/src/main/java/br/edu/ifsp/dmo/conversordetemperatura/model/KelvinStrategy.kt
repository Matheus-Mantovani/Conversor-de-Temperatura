package br.edu.ifsp.dmo.conversordetemperatura.model

object KelvinStrategy: ConversorTemperatura {

    override fun converter(temperature: Double, strategy: ConversorTemperatura): Double {
        return when(strategy) {
            is CelsiusStrategy -> celsiusToKelvin(temperature)
            is FahrenheitStrategy -> fahrenheitToKelvin(temperature)
            else -> temperature
        }
    }

    override fun getScale() = "K"

    private fun celsiusToKelvin(temperature: Double) = temperature + 273.15

    private fun fahrenheitToKelvin(temperature: Double) = (temperature + 459.67) / 1.8
}