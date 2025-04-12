package com.tesisforero.motosecure.models

data class DniRequest(
    val token: String,
    val type_document: String,
    val document_number: String
)

data class UserInfoResponse(
    val data: UserData
)

data class UserData(
    val name: String,
    val surname: String
)