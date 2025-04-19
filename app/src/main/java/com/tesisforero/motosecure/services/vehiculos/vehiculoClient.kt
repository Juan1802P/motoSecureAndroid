package com.tesisforero.motosecure.services.vehiculos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.18.29:8000"  // IP de tu servidor

    val vehiculoService: ApiServiceVehiculo by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Usando Gson para convertir JSON
            .build()
            .create(ApiServiceVehiculo::class.java)
    }
}
