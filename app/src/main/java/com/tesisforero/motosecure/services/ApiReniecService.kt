package com.tesisforero.motosecure.services

import com.tesisforero.motosecure.models.DniRequest
import com.tesisforero.motosecure.models.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiReniecService {

    @Headers("Content-Type: application/json")
    @POST("query")  // Cambia la ruta según sea necesario
    suspend fun getUserInfoByDni(@Body requestBody: DniRequest): UserInfoResponse
}