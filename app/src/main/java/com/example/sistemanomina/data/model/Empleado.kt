package com.example.sistemanomina.data.model

import com.google.gson.annotations.SerializedName

data class Empleado(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("puesto") val puesto: String,
    @SerializedName("salario_base") val salarioBase: Double,
    @SerializedName("email") val email: String
)