package com.tesisforero.motosecure.models

data class LoginRequest(
    val dni_usu: String,
    val password_usu: String
)

data class LoginResponse(
    val message: String,
    val success: Boolean
)

data class RegisterRequest(
    val dni_usu: String,
    val password_usu: String,
    val name_usu: String,
    val lastname_usu: String,
    val phone_usu: String,
    val email_usu: String
)

data class RegisterResponse(
    val message: String,
    val success: Boolean
)