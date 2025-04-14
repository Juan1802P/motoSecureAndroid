package com.tesisforero.motosecure.viewmodel.google

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {

    private val _suggestions = mutableStateListOf<AutocompletePrediction>()
    val suggestions: List<AutocompletePrediction> get() = _suggestions

    private val _selectedLatLng = MutableStateFlow<LatLng?>(null)
    val selectedLatLng: StateFlow<LatLng?> get() = _selectedLatLng

    private val _query = MutableStateFlow("")
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    private lateinit var placesClient: PlacesClient

    fun initPlaces(context: Context) {
        if (!::placesClient.isInitialized) {
            placesClient = Places.createClient(context)
        }

        observeQueryChanges()
    }

    private fun observeQueryChanges() {
        viewModelScope.launch {
            _query
                .debounce(500) // espera 500ms después de que el usuario deje de escribir
                .filter { it.length > 2 } // mínimo 3 caracteres para buscar
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchPlaces(query)
                }
        }
    }

    fun searchPlaces(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                _suggestions.clear()
                _suggestions.addAll(response.autocompletePredictions)
            }
            .addOnFailureListener { exception ->
                Log.e("PlaceViewModel", "Autocomplete error: ${exception.message}")
            }
    }

    fun fetchPlaceDetails(placeId: String) {
        val placeFields = listOf(Place.Field.LAT_LNG)

        val request = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                _selectedLatLng.value = response.place.latLng
            }
            .addOnFailureListener { exception ->
                Log.e("PlaceViewModel", "FetchPlace error: ${exception.message}")
            }
    }
}
