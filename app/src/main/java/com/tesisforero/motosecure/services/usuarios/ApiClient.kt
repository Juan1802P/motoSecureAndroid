package com.tesisforero.motosecure.services.usuarios

import com.tesisforero.motosecure.models.usuarios.LoginRequest
import com.tesisforero.motosecure.models.usuarios.LoginResponse
import com.tesisforero.motosecure.models.usuarios.RegisterRequest
import com.tesisforero.motosecure.models.usuarios.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login/")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>

    @POST("/create/")
    suspend fun  create(@Body credentials: RegisterRequest): Response<RegisterResponse>
}
