package com.tesisforero.motosecure.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

// Componente para el mapa
@Composable
fun GoogleMapView(
    origenLatLng: LatLng?,
    destinoLatLng: LatLng?
) {
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { mapView },
        update = { mapView ->
            mapView.getMapAsync { googleMap ->
                googleMap.clear()

                if (origenLatLng != null && destinoLatLng != null) {
                    googleMap.addMarker(MarkerOptions().position(origenLatLng).title("Origen"))
                    googleMap.addMarker(MarkerOptions().position(destinoLatLng).title("Destino"))

                    val bounds = LatLngBounds.builder()
                        .include(origenLatLng)
                        .include(destinoLatLng)
                        .build()

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                } else if (origenLatLng != null) {
                    googleMap.addMarker(MarkerOptions().position(origenLatLng).title("Origen"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origenLatLng, 14f))
                }
            }
        }
    )
}


@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return mapView
}
