package br.edu.ifsp.dmo.conversordetemperatura.model

interface ConversorTemperatura {

    fun converter(temperature: Double, strategy: ConversorTemperatura): Double

    fun getScale(): String
}