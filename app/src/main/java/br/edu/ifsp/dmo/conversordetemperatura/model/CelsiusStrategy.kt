package br.edu.ifsp.dmo.conversordetemperatura.model

object CelsiusStrategy: ConversorTemperatura {

    override fun converter(temperature: Double, strategy: ConversorTemperatura): Double {
        return when(strategy) {
            is KelvinStrategy -> kelvinToCelsius(temperature)
            is FahrenheitStrategy -> fahrenheitToCelsius(temperature)
            else -> temperature
        }
    }

    override fun getScale() = "ºC"

    private fun kelvinToCelsius(temperature: Double) = temperature - 273.15

    private fun fahrenheitToCelsius(temperature: Double) = (temperature - 32) / 1.8
}