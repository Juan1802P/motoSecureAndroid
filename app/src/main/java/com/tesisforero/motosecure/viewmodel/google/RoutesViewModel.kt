package com.tesisforero.motosecure.viewmodel.google

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesisforero.motosecure.models.LatLng
import com.tesisforero.motosecure.models.Location
import com.tesisforero.motosecure.models.OriginOrDestination
import com.tesisforero.motosecure.models.RouteRequest
import com.tesisforero.motosecure.services.GoogleMapsApiService
import com.tesisforero.motosecure.services.RetrofitGoogleClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RouteViewModel : ViewModel() {

    private val _tiempoEstimado = MutableStateFlow("")

    val tiempoEstimado: StateFlow<String> = _tiempoEstimado

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiService: GoogleMapsApiService =
        RetrofitGoogleClient.getClient("AIzaSyD3W_DclkBMKm03SvHaJ150Q_uzypbMm-E")

    fun obtenerRuta(origenLat: Double, origenLng: Double, destinoLat: Double, destinoLng: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = RouteRequest(
                    origin = OriginOrDestination(Location(LatLng(origenLat, origenLng))),
                    destination = OriginOrDestination(Location(LatLng(destinoLat, destinoLng)))
                )
                val response = apiService.getRoute(request)

                val duracion = response.routes.firstOrNull()?.duration ?: "No encontrada"
                val distancia = response.routes.firstOrNull()?.distanceMeters ?: 0
                _tiempoEstimado.value = "Duración: $duracion, Distancia: ${distancia}m"
            } catch (e: Exception) {
                _tiempoEstimado.value = "Error al obtener la ruta"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
