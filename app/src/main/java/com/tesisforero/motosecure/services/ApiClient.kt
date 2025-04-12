package com.tesisforero.motosecure.services

import com.tesisforero.motosecure.models.LoginRequest
import com.tesisforero.motosecure.models.LoginResponse
import com.tesisforero.motosecure.models.RegisterRequest
import com.tesisforero.motosecure.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login/")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>

    @POST("/create/")
    suspend fun  create(@Body credentials: RegisterRequest): Response<RegisterResponse>
}
