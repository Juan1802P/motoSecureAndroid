package com.tesisforero.motosecure.ui.components

import com.tesisforero.motosecure.viewmodel.google.PlaceViewModel
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

@Composable
fun PlaceAutoCompleteTextField(
    label: String,
    viewModel: PlaceViewModel,
    onPlaceSelected: (LatLng) -> Unit
) {
    var query by remember {mutableStateOf("")}
    var showSuggestions by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                showSuggestions = true
                viewModel.searchPlaces(query)
            },
            label = { Text(label) }
        )

        if (showSuggestions && viewModel.suggestions.isNotEmpty()) {
            LazyColumn {
                items(viewModel.suggestions.size) { index ->
                    val prediction = viewModel.suggestions[index]
                    Text(
                        text = prediction.getFullText(null).toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                query = prediction.getFullText(null).toString()
                                showSuggestions = false
                                viewModel.fetchPlaceDetails(prediction.placeId)
                            }
                            .padding(8.dp)
                    )
                }
            }
        }



        LaunchedEffect(viewModel.selectedLatLng.collectAsState().value) {
            viewModel.selectedLatLng.value?.let {
                onPlaceSelected(it)
            }
        }
    }
}
