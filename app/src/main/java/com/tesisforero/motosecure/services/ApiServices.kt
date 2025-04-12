package com.tesisforero.motosecure.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL_LOCAL = "http://192.168.18.29:8000"
    private const val BASE_URL_EXTERNAL = "https://api.consultasperu.com/api/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit para tu API local
    private val retrofitLocal = Retrofit.Builder()
        .baseUrl(BASE_URL_LOCAL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Retrofit para la API externa (consulta DNI)
    private val retrofitExternal = Retrofit.Builder()
        .baseUrl(BASE_URL_EXTERNAL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Servicio para mis APIs
    val apiService: ApiService = retrofitLocal.create(ApiService::class.java)

    // Servicio para la consulta de DNI
    val externalApiService: ApiReniecService = retrofitExternal.create(ApiReniecService::class.java)
}
