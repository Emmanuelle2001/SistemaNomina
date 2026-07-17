package com.example.sistemanomina.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sistemanomina.data.model.Empleado
import com.example.sistemanomina.ui.screens.FormularioScreen
import com.example.sistemanomina.ui.screens.PrincipalScreen
import com.example.sistemanomina.ui.viewmodel.NominaViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: NominaViewModel = viewModel()
    var empleadoSeleccionado by remember { mutableStateOf<Empleado?>(null) }

    NavHost(navController = navController, startDestination = "principal") {
        composable("principal") {
            PrincipalScreen(
                viewModel = viewModel,
                onNavigateToAlta = {
                    empleadoSeleccionado = null
                    navController.navigate("formulario")
                },
                onNavigateToEdicion = { emp ->
                    empleadoSeleccionado = emp
                    navController.navigate("formulario")
                }
            )
        }
        composable("formulario") {
            FormularioScreen(
                viewModel = viewModel,
                empleadoEdicion = empleadoSeleccionado,
                onBack = { navController.popBackStack() }
            )
        }
    }
}