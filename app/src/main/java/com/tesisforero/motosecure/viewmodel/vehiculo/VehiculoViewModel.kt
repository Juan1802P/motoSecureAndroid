package com.tesisforero.motosecure.viewmodel.vehiculo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesisforero.motosecure.models.vehiculos.VehiculoInfo
import com.tesisforero.motosecure.services.vehiculos.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class vehiculoInfoViewModel: ViewModel(){

    private val _vehiculoInfo = MutableStateFlow<VehiculoInfo?>(null)
    val vehiculoInfo: StateFlow<VehiculoInfo?> = _vehiculoInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)

    fun fetchVehiculoInfo(placa: String){
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try{
                val response = RetrofitClient.vehiculoService.getVehiculoPorPlaca(placa)

                if(true){
                    _vehiculoInfo.value = response.body()
                }else{
                    _error.value = "No se encontraron registros"
                }
            }catch (e: Exception){
                Log.e("VehiculoViewModel","Error la buscar vehiculo", e)
                _error.value = "Error al obtener la informacion"
            }finally {
                _isLoading.value = false
            }
        }
    }
}