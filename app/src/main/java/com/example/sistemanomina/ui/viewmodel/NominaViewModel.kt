package com.example.sistemanomina.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemanomina.data.model.Empleado
import com.example.sistemanomina.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.io.IOException

class NominaViewModel : ViewModel() {

    var empleados by mutableStateOf<List<Empleado>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun obtenerEmpleados() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                empleados = RetrofitClient.apiService.getEmpleados()
            } catch (e: IOException) {
                errorMessage = "No se pudo conectar al servidor Flask. Verifica que esté encendido."
            } catch (e: Exception) {
                errorMessage = "Error al procesar los datos del servidor."
            } finally {
                isLoading = false
            }
        }
    }

    fun guardarEmpleado(empleado: Empleado, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                if (empleado.id != null) {
                    RetrofitClient.apiService.actualizarEmpleado(empleado.id, empleado)
                } else {
                    RetrofitClient.apiService.crearEmpleado(empleado)
                }
                obtenerEmpleados()
                onSuccess()
            } catch (e: Exception) {
                errorMessage = "Error al guardar el registro en el servidor."
            } finally {
                isLoading = false
            }
        }
    }

    fun eliminarEmpleado(id: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                RetrofitClient.apiService.eliminarEmpleado(id)
                obtenerEmpleados()
            } catch (e: Exception) {
                errorMessage = "No se pudo eliminar el empleado."
            } finally {
                isLoading = false
            }
        }
    }
}