package com.example.sistemanomina.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sistemanomina.data.model.Empleado
import com.example.sistemanomina.ui.viewmodel.NominaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
    viewModel: NominaViewModel,
    onNavigateToAlta: () -> Unit,
    onNavigateToEdicion: (Empleado) -> Unit
) {
    var empleadoAEliminar by remember { mutableStateOf<Empleado?>(null) }

    LaunchedEffect(Unit) {
        viewModel.obtenerEmpleados()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Nómina de Empleados") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAlta) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewModel.errorMessage?.let { msg ->
                AlertDialog(
                    onDismissRequest = { viewModel.errorMessage = null },
                    title = { Text("Aviso del Sistema") },
                    text = { Text(msg) },
                    confirmButton = { Button(onClick = { viewModel.errorMessage = null }) { Text("Aceptar") } }
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(viewModel.empleados) { emp ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(emp.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Puesto: ${emp.puesto}")
                                Text("Salario: $${emp.salarioBase}")
                                Text("Email: ${emp.email}")
                            }
                            Row {
                                IconButton(onClick = { onNavigateToEdicion(emp) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = { empleadoAEliminar = emp }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            }

            empleadoAEliminar?.let { emp ->
                AlertDialog(
                    onDismissRequest = { empleadoAEliminar = null },
                    title = { Text("Confirmar acción") },
                    text = { Text("¿Estás seguro de que deseas eliminar a ${emp.nombre} de la nómina?") },
                    confirmButton = {
                        Button(onClick = {
                            emp.id?.let { viewModel.eliminarEmpleado(it) }
                            empleadoAEliminar = null
                        }) { Text("Eliminar") }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { empleadoAEliminar = null }) { Text("Cancelar") }
                    }
                )
            }
        }
    }
}