package com.tesisforero.motosecure.viewmodel.usuario

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.tesisforero.motosecure.models.usuarios.RegisterRequest
import com.tesisforero.motosecure.models.usuarios.RegisterResponse
import com.tesisforero.motosecure.services.usuarios.RetrofitClient
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _registerResult = MutableStateFlow<RegisterResult?>(null)
    val registerResult: StateFlow<RegisterResult?> = _registerResult

    fun register(dni: String, password: String, name: String, lastname: String, phone: String, email: String){
        viewModelScope.launch {
            try{
                val response: Response<RegisterResponse> = RetrofitClient.apiService.create(RegisterRequest(dni, password, name, lastname, phone, email))
                if(response.isSuccessful && response.body() != null){
                    val registerResponse = response.body()!!
                    Log.d("LoginResponse", "Success: ${registerResponse.success}, Message: ${registerResponse.message}")
                    _registerResult.value = RegisterResult(success = registerResponse.success, message = registerResponse.message)
                }else{
                    _registerResult.value = RegisterResult(success = false, message = "Error: ${response.code()} ${response.message()}")
                    Log.e("LoginError", "Error de registro: ${response.code()} ${response.message()}")
                    Log.d("RegisterData", "dni=$dni, pass=$password, name=$name, last=$lastname, phone=$phone, email=$email")

                }
            }catch (e: Exception){
                _registerResult.value = RegisterResult(success = false, message = "Excepción: ${e.localizedMessage}" )
                Log.e("LoginException", "Excepción en registro", e)
            }


        }
    }


}

data class RegisterResult(val success: Boolean, val message: String)
