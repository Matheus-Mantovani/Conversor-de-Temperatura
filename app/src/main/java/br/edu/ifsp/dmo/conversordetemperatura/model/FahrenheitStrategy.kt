package br.edu.ifsp.dmo.conversordetemperatura.model

object FahrenheitStrategy: ConversorTemperatura {

    override fun converter(temperature: Double, strategy: ConversorTemperatura): Double {
        return when(strategy) {
            is CelsiusStrategy -> celsiusToFahrenheit(temperature)
            is KelvinStrategy -> kelvinToFahrenheit(temperature)
            else -> temperature
        }
    }

    override fun getScale() = "ºF"

    private fun celsiusToFahrenheit(temperature: Double) = 1.8 * temperature + 32

    private fun kelvinToFahrenheit(temperature: Double) = (temperature * 1.8) - 459.67
}