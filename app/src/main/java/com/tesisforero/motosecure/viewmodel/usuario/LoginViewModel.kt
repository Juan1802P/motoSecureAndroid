package com.tesisforero.motosecure.viewmodel.usuario

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.tesisforero.motosecure.models.LoginRequest
import com.tesisforero.motosecure.models.LoginResponse
import com.tesisforero.motosecure.services.RetrofitClient
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult: StateFlow<LoginResult?> = _loginResult

    fun login(dni: String, password: String) {
        viewModelScope.launch {
            try{
                val response: Response<LoginResponse> = RetrofitClient.apiService.login(LoginRequest(dni,password))
                if(response.isSuccessful && response.body() != null){
                    val loginResponse = response.body()!!
                    Log.d("LoginResponse", "Success: ${loginResponse.success}, Message: ${loginResponse.message}")
                    _loginResult.value = LoginResult(success = loginResponse.success, message = loginResponse.message)
                }else{
                    _loginResult.value = LoginResult(success = false, message = "Error: ${response.code()} ${response.message()}")
                    Log.e("LoginError", "Error de login: ${response.code()} ${response.message()}")
                }
            }catch (e: Exception){
                _loginResult.value = LoginResult(success = false, message = "Excepción: ${e.localizedMessage}" )
                Log.e("LoginException", "Excepción en login", e)
            }
        }
    }
}

data class LoginResult(val success: Boolean, val message: String)
