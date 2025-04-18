package com.tesisforero.motosecure.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.tesisforero.motosecure.ui.theme.emerald_dark


class SafeDates : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafeDatesScreen()
        }
    }
}

@Composable
fun SafeDatesScreen() {

    var dni by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var licencia by remember { mutableStateOf("") }

    var placa by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var asociacion by remember { mutableStateOf("") }

    var onclickSearch by remember { mutableStateOf(false)}
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        // Parte superior
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
                    text = "INICIAR VIAJE",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = placa,
                        onValueChange = { placa = it },
                        label = { Text("placa", color = Color.White) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onclickSearch = true
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Buscar", color = emerald_dark)
                    }
                }
            }
        }

        // Parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(Color.White)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Chofer", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = emerald_dark)
                Spacer(modifier = Modifier.height(8.dp))
                LabelItem("DNI", dni)
                LabelItem("Chofer", nombres)
                LabelItem("Teléfono", telefono)
                LabelItem("Licencia", licencia)

                Spacer(modifier = Modifier.height(24.dp))

                Text("Vehículo", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = emerald_dark)
                Spacer(modifier = Modifier.height(8.dp))
                LabelItem("Placa", placa)
                LabelItem("Modelo", modelo)
                LabelItem("Color", color)
                LabelItem("Asociación", asociacion)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if(onclickSearch){

                        }else{
                            Toast.makeText(context, "Debes realziar la verificacion de seguridad ", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = emerald_dark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Iniciar Viaje", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Barra inferior
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

@Composable
fun LabelItem(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(100.dp)
        )
        Text(text = value)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSafeDatesScreen() {
    SafeDatesScreen()
}
