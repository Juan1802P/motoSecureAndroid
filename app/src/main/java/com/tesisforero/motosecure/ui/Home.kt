package com.tesisforero.motosecure.ui

import android.location.Geocoder
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
import com.tesisforero.motosecure.R
import com.tesisforero.motosecure.ui.theme.emerald_dark
import com.tesisforero.motosecure.viewmodel.google.RouteViewModel

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userName = intent.getStringExtra("userName")
            Log.d("Datos de Login", "name: $userName")
            HomeScreen(userName = userName?: "ERROR")
        }
    }
}

//

@Composable
fun HomeScreen(userName: String, viewModel: RouteViewModel = viewModel()) {

    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }

    val routeViewModel: RouteViewModel = viewModel()
    val tiempoEstimado by routeViewModel.tiempoEstimado.collectAsState()

    val context = LocalContext.current

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
            ){
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

        // Parte intermedia blanca con campos de texto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(Color.White)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(50.dp))

                // Campo Origen
                OutlinedTextField(
                    value = origen,
                    onValueChange = { origen = it },
                    label = { Text("Origen") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Campo Destino
                OutlinedTextField(
                    value = destino,
                    onValueChange = { destino = it },
                    label = { Text("Destino") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Etiqueta Tiempo Estimado
                Text(
                    text = "Datos del Viaje: $tiempoEstimado",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Botón Verificar Datos
                Button(
                    onClick = {
                       try{
                           val geocoder = Geocoder(context)
                           val origenList = geocoder.getFromLocationName(origen, 1)
                           val destinoList = geocoder.getFromLocationName(destino, 1)

                           if (!origenList.isNullOrEmpty() && !destinoList.isNullOrEmpty()) {
                               val origenLatLng = origenList[0]
                               val destinoLatLng = destinoList[0]

                               Log.d("Geocoding", "Origen: ${origenLatLng.latitude}, ${origenLatLng.longitude}")
                               Log.d("Geocoding", "Destino: ${destinoLatLng.latitude}, ${destinoLatLng.longitude}")

                               viewModel.obtenerRuta(
                                   origenLat = origenLatLng.latitude,
                                   origenLng = origenLatLng.longitude,
                                   destinoLat = destinoLatLng.latitude,
                                   destinoLng = destinoLatLng.longitude
                               )
                           } else {
                               Log.e("Geocoding", "Dirección no encontrada")
                           }

                       }catch (e: Exception){
                           Log.e("Geocoding", "Error al obtener coordenadas: ${e.message}")
                       }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = emerald_dark)
                ) {
                    Text(text = "Siguente", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Franja verde en la parte inferior con iconos
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
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Configuración",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Notificaciones",
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
    HomeScreen(userName = "JUAN")
}
