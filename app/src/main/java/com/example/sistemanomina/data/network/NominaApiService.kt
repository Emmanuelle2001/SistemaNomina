package com.example.sistemanomina.data.network

import com.example.sistemanomina.data.model.Empleado
import retrofit2.Response
import retrofit2.http.*

interface NominaApiService {
    @GET("empleados")
    suspend fun getEmpleados(): List<Empleado>

    @POST("empleados")
    suspend fun crearEmpleado(@Body empleado: Empleado): Empleado

    @PUT("empleados/{id}")
    suspend fun actualizarEmpleado(@Path("id") id: Int, @Body empleado: Empleado): Empleado

    @DELETE("empleados/{id}")
    suspend fun eliminarEmpleado(@Path("id") id: Int): Response<Unit>
}