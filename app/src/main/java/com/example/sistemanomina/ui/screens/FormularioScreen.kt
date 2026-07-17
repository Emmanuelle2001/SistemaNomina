package com.example.sistemanomina.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sistemanomina.data.model.Empleado
import com.example.sistemanomina.ui.viewmodel.NominaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: NominaViewModel,
    empleadoEdicion: Empleado? = null,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(empleadoEdicion?.nombre ?: "") }
    var puesto by remember { mutableStateOf(empleadoEdicion?.puesto ?: "") }
    var salario by remember { mutableStateOf(empleadoEdicion?.salarioBase?.toString() ?: "") }
    var email by remember { mutableStateOf(empleadoEdicion?.email ?: "") }
    var errorValidacion by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (empleadoEdicion == null) "Registrar Empleado" else "Modificar Datos") }) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = puesto, onValueChange = { puesto = it }, label = { Text("Puesto Ocupado") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = salario, onValueChange = { salario = it }, label = { Text("Salario Mensual") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth())

            errorValidacion?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val salarioDouble = salario.toDoubleOrNull()
                    if (nombre.isBlank() || puesto.isBlank() || salario.isBlank() || email.isBlank()) {
                        errorValidacion = "Por favor, llene todos los campos."
                    } else if (salarioDouble == null || salarioDouble <= 0) {
                        errorValidacion = "El salario debe ser una cantidad numérica mayor a cero."
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorValidacion = "El formato de correo no es válido."
                    } else {
                        val emp = Empleado(id = empleadoEdicion?.id, nombre = nombre, puesto = puesto, salarioBase = salarioDouble, email = email)
                        viewModel.guardarEmpleado(emp) { onBack() }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}