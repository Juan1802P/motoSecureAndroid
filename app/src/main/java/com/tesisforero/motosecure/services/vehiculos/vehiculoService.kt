package com.tesisforero.motosecure.services.vehiculos

import com.tesisforero.motosecure.models.vehiculos.VehiculoInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceVehiculo {
    @GET("/vehiculo/datos/{placa}")
    suspend fun getVehiculoPorPlaca(@Path("placa") placa: String): Response<VehiculoInfo>
}
