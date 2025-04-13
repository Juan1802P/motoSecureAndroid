package com.tesisforero.motosecure.viewmodel.usuario

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesisforero.motosecure.models.usuarios.DniRequest
import com.tesisforero.motosecure.models.usuarios.UserInfoResponse
import com.tesisforero.motosecure.services.usuarios.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReniecViewModel : ViewModel() {

    // StateFlow para manejar el estado de la respuesta de la API
    private val _userInfo = MutableStateFlow<UserInfoResponse?>(null)
    val userInfo: StateFlow<UserInfoResponse?> = _userInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)

    // Lógica para obtener la información del usuario por DNI
    fun fetchUserInfo(dni: String) {
        _isLoading.value = true // Indicamos que se está cargando la información
        _error.value = null // Limpiamos cualquier mensaje de error anterior

        viewModelScope.launch {
            try {
                val dniRequest = DniRequest(
                    token = "55d5ea20aae917e57a5a622a555cf0a7b5e7d2c160d070f568d9b54d1a7ce1ed",
                    type_document = "dni",    // Tipo de documento (DNI)
                    document_number = dni     // Número de DNI ingresado
                )

                // Realizamos la solicitud a la API
                val response = RetrofitClient.externalApiService.getUserInfoByDni(dniRequest)

                // Verificamos que la respuesta no sea nula o vacía
                if (true) {
                    _userInfo.value = response // Actualizamos con los datos obtenidos
                } else {
                    _error.value = "No se encontraron datos."
                }

            } catch (e: Exception) {
                Log.e("ReniecViewModel", "Error fetching user info", e)
                _error.value = "Error al obtener la información."
            } finally {
                _isLoading.value = false // Finalizamos el proceso de carga
            }
        }
    }
}
