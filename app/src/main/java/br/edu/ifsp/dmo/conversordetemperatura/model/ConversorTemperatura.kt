package br.edu.ifsp.dmo.conversordetemperatura.model

interface ConversorTemperatura {

    fun converter(temperature: Double): Double

    fun getScale(): String
}