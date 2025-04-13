package com.tesisforero.motosecure.viewmodel.google

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlaceViewModel : ViewModel() {

    private val _suggestions = mutableStateListOf<AutocompletePrediction>()
    val suggestions: List<AutocompletePrediction> get() = _suggestions

    private val _selectedLatLng = MutableStateFlow<LatLng?>(null)
    val selectedLatLng: StateFlow<LatLng?> get() = _selectedLatLng

    private lateinit var placesClient: PlacesClient

    fun initPlaces(context: Context) {
        if (!::placesClient.isInitialized) {
            placesClient = Places.createClient(context)
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
    }

    fun fetchPlaceDetails(placeId: String) {
        val placeFields = listOf(Place.Field.LAT_LNG)

        val request = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                _selectedLatLng.value = response.place.latLng
            }
    }
}
