package com.tesisforero.motosecure.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.tesisforero.motosecure.BuildConfig
import com.tesisforero.motosecure.R
import com.tesisforero.motosecure.ui.components.GoogleMapView
import com.tesisforero.motosecure.ui.components.PlaceAutoCompleteTextField
import com.tesisforero.motosecure.ui.theme.emerald_dark
import com.tesisforero.motosecure.viewmodel.google.RouteViewModel
import com.tesisforero.motosecure.viewmodel.google.PlaceViewModel


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        }

        setContent {
            val userName = intent.getStringExtra("userName")
            Log.d("Datos de Login", "name: $userName")
            HomeScreen(
                userName = userName ?: "ERROR",
                navigateToSafeDates = {navigateToSafeDates()}
            )

        }
    }

    private fun navigateToSafeDates() {
        startActivity(Intent(this, SafeDates::class.java))
    }
}


@Composable
fun HomeScreen(
    userName: String,
    //viewModel: RouteViewModel = viewModel(),
    navigateToSafeDates: ()-> Unit
) {


    val routeViewModel: RouteViewModel = viewModel()
    val tiempoEstimado by routeViewModel.tiempoEstimado.collectAsState()

    val origenViewModel = remember { PlaceViewModel() }
    val destinoViewModel = remember { PlaceViewModel() }
    var origenLatLng by remember { mutableStateOf<LatLng?>(null) }
    var destinoLatLng by remember { mutableStateOf<LatLng?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        origenViewModel.initPlaces(context)
        destinoViewModel.initPlaces(context)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Parte superior 1/4 color verde
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(emerald_dark)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = "¡Bienvenido $userName!",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "¿CUAL ES TU SIGUIENTE DESTINO?",
                    color = Color.White,
                    fontSize = 10.sp,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logot),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 20.dp)
            )
        }

        // Parte intermedia blanca
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    // Campo de ORIGEN

                    PlaceAutoCompleteTextField(
                        label = "Origen",
                        viewModel = origenViewModel,
                        onPlaceSelected = { latLng ->
                            origenLatLng = latLng
                            Log.d("Origen", "LatLng: $latLng")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de DESTINO
                    PlaceAutoCompleteTextField(
                        label = "Destino",
                        viewModel = destinoViewModel,
                        onPlaceSelected = { latLng ->
                            destinoLatLng = latLng
                            Log.d("Destino", "LatLng: $latLng")
                        }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Etiqueta Tiempo Estimado
                    Text(
                        text = tiempoEstimado,
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Mostrar el mapa aquí
                    if (origenLatLng != null && destinoLatLng != null) {

                        /*if (origenLatLng != null && destinoLatLng != null) {
                            viewModel.obtenerRuta(
                                origenLat = origenLatLng!!.latitude,
                                origenLng = origenLatLng!!.longitude,
                                destinoLat = destinoLatLng!!.latitude,
                                destinoLng = destinoLatLng!!.longitude
                            )
                        } else {
                            Log.e("Ruta", "Faltan coordenadas de origen o destino")
                        }*/

                        GoogleMapView(
                            origenLatLng = origenLatLng,
                            destinoLatLng = destinoLatLng
                        )
                    }

                    // Botón Verificar Datos
                    Button(
                        onClick = {
                            navigateToSafeDates()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = emerald_dark)
                    ) {
                        Text(
                            text = "Siguiente",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Barra inferior con íconos
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(emerald_dark)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.StackedLineChart,
                    contentDescription = "Estadísticas",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(userName = "JUAN", navigateToSafeDates = {})
}
